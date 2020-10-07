package cashier.servlet.cashierCommands;

import cashier.Path;
import cashier.Statuses;
import cashier.dao.CategoriesDaoImpl;
import cashier.dao.ProductsDaoImpl;
import cashier.dao.entity.Category;
import cashier.dao.entity.Product;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.User;
import cashier.servlet.Command;
import cashier.util.GenerateReceiptNumber;
import cashier.util.CalculateValuesByReceipts;
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
            session.setAttribute("goods", productsDao.findAllPresentProducts());

            categoriesDao = new CategoriesDaoImpl();
            session.setAttribute("categories", categoriesDao.getAllCategories());

            List<Product> products = (List<Product>) session.getAttribute("products");
            List<Receipt> receipts = (List<Receipt>) session.getAttribute("basket");
            if (null == products || products.isEmpty()) products = new LinkedList<>();
            if (null == receipts || receipts.isEmpty()) receipts = new LinkedList<>();

            if (request.getParameter("command").equals("add_to_basket")) {
                Product product = productsDao.getProductsById(Integer.parseInt(request.getParameter("goods_id")));
                product.setCount(product.getCount() - Integer.parseInt(request.getParameter("count")));

                if (!products.contains(product)) {
                    Receipt receipt = new Receipt();
                    receipt.setCount(Integer.parseInt(request.getParameter("count")));
                    if (null == receipts || receipts.isEmpty())
                        receipt.setReceiptId(GenerateReceiptNumber.getReceiptNo());
                    else
                        receipt.setReceiptId(receipts.get(0).getReceiptId());
                    receipt.setPrice(product.getPrice());
                    receipt.setProductName(product.getName());
                    receipt.setStatus(Statuses.CREATED.shortValue());
                    receipt.setProductID(product.getId());
                    receipt.setUserID(user.getId());

                    products.add(product);
                    receipts.add(receipt);
                    session.setAttribute("products", products);
                    session.setAttribute("basket", receipts);
                    session.setAttribute("total_price", CalculateValuesByReceipts.getTotalAmount(receipts) / 100.0);
                }
            } else if (request.getParameter("command").equals("remove_from_basket")) {

                Product product = productsDao.getProductsByName(request.getParameter("cancel_product_name"));
                product.setCount(Integer.parseInt(request.getParameter("cancel_product_count")));

                Receipt receipt = new Receipt();
                receipt.setCount(product.getCount());
                if (null == receipts || receipts.isEmpty())
                    receipt.setReceiptId(GenerateReceiptNumber.getReceiptNo());
                else
                    receipt.setReceiptId(receipts.get(0).getReceiptId());
                receipt.setPrice(product.getPrice());
                receipt.setStatus(Statuses.CREATED.shortValue());
                receipt.setProductID(product.getId());
                receipt.setUserID(user.getId());

                products.remove(product);
                receipts.remove(receipt);
                session.setAttribute("products", products);
                session.setAttribute("basket", receipts);
                session.setAttribute("total_price", CalculateValuesByReceipts.getTotalAmount(receipts) / 100.0);
            } else if (request.getParameter("command").equals("clear_basket")) {
                products = null;
                receipts = null;
                session.setAttribute("products", null);
                session.setAttribute("basket", null);
                session.setAttribute("total_price", 0.00);
            } else if (request.getParameter("command").equals("refactor_product")) {

                Product product = new Product();
                product.setPrice(Long.parseLong(request.getParameter("count")));
                product.setCount(Integer.parseInt(request.getParameter("price")));
                product.setId(Integer.parseInt(request.getParameter("goods_id")));

                if (productsDao.updateProduct(product)) {
                    forward = Path.SUCCESS;
                    return forward;
                } else {
                    throw new Exception();
                }

            } else if (request.getParameter("command").equals("create_category")) {
                Category category = new Category();
                category.setName(request.getParameter("name"));
                if (categoriesDao.insertCategory(category)) {
                    forward = Path.SUCCESS;
                    return forward;
                } else {
                    throw new Exception();
                }
            } else if (request.getParameter("command").equals("create_product")) {
                Product product = new Product();
                product.setName(request.getParameter("name"));
                product.setPrice(Long.parseLong(request.getParameter("price")));
                product.setCount(Integer.parseInt(request.getParameter("count")));
                product.setWeight(Long.parseLong(request.getParameter("weight")));
                product.setCategoriesId(Integer.parseInt(request.getParameter("category_id")));
                if (productsDao.insertProduct(product)) {
                    forward = Path.SUCCESS;
                    return forward;
                } else {
                    throw new Exception();
                }
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

}
