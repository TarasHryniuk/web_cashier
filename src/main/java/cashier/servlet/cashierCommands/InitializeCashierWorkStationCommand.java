package cashier.servlet.cashierCommands;

import cashier.Path;
import cashier.Statuses;
import cashier.dao.CategoriesDaoImpl;
import cashier.dao.ProductsDaoImpl;
import cashier.dao.entity.Product;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.User;
import cashier.servlet.Command;
import cashier.util.GenerateReceiptNumber;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  05.10.2020
 * email : hryniuk.t@gmail.com
 */
public class InitializeCashierWorkStationCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(InitializeCashierWorkStationCommand.class);
    private CategoriesDaoImpl categoriesDao;
    private ProductsDaoImpl productsDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        try {
            productsDao = new ProductsDaoImpl();
            request.setAttribute("goods", productsDao.findAllPresentProducts());

            List<Product> products = (List<Product>) session.getAttribute("products");
            List<Receipt> receipts = (List<Receipt>) session.getAttribute("basket");
            if (null == products || products.isEmpty()) products = new LinkedList<>();
            if (null == receipts || receipts.isEmpty()) receipts = new LinkedList<>();

            if (request.getParameter("command").equals("add_to_basket")) {
                Product product = productsDao.getProductsById(Integer.parseInt(request.getParameter("goods_id")));
                product.setCount(Integer.parseInt(request.getParameter("count")));

                if (!products.contains(product)) {
                    Receipt receipt = new Receipt();
                    receipt.setCount(product.getCount());
                    if (null != receipts || !receipts.isEmpty())
                        receipt.setReceiptId(receipts.get(0).getReceiptId());
                    else
                        receipt.setReceiptId(GenerateReceiptNumber.getReceiptNo());
                    receipt.setPrice(product.getPrice());
                    receipt.setStatus(Statuses.CREATED.shortValue());
                    receipt.setProductID(product.getId());
                    receipt.setUserID(user.getId());

                    products.add(product);
                    receipts.add(receipt);
                    session.setAttribute("products", products);
                    session.setAttribute("basket", receipts);
                    session.setAttribute("total_price", getTotalAmount(receipts) / 100.0);
                }
            } else if (request.getParameter("command").equals("remove_from_basket")) {

                Product product = productsDao.getProductsById(Integer.parseInt(request.getParameter("cancel_product_id")));
                product.setCount(Integer.parseInt(request.getParameter("cancel_product_count")));

                Receipt receipt = new Receipt();
                receipt.setCount(product.getCount());
                receipt.setPrice(product.getPrice());
                receipt.setStatus(Statuses.CREATED.shortValue());
                receipt.setProductID(product.getId());
                receipt.setUserID(user.getId());

                products.remove(product);
                receipts.remove(receipt);
                session.setAttribute("products", products);
                session.setAttribute("basket", receipts);
                session.setAttribute("total_price", getTotalAmount(receipts) / 100.0);
            } else if (request.getParameter("command").equals("clear_basket")) {
                products = null;
                receipts = null;
                session.setAttribute("products", null);
                session.setAttribute("basket", null);
                session.setAttribute("total_price", 0.00);
            }

        } catch (Exception e) {
            errorMessage = "Something went wrong....";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error(e);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        forward = Path.PAGE_CASHIER_WORK_STATION;

        LOGGER.debug("Command finished");
        return forward;
    }


    private Long getTotalAmount(List<Receipt> receipts) {
        Long startPrice = 0L;

        for (Receipt receipt : receipts) {
            startPrice += (receipt.getPrice() * receipt.getCount());
        }

        return startPrice;
    }

}
