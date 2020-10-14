package cashier.servlet.cashierCommands;

import cashier.Path;
import cashier.Statuses;
import cashier.dao.ProductsDaoImpl;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Product;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.servlet.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  05.10.2020
 * email : hryniuk.t@gmail.com
 */
public class ProcessingReceiptCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(ProcessingReceiptCommand.class);
    private ReceiptsDaoImpl receiptsDao;
    private ProductsDaoImpl productsDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();
        Role userRole = Role.getRole((User) session.getAttribute("user"));

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        try {
            receiptsDao = new ReceiptsDaoImpl();
            productsDao = new ProductsDaoImpl();

            if (request.getParameter("command").equals("pay_basket")) {
                List<Receipt> receipts = (List<Receipt>) session.getAttribute("basket");
                List<Product> products = (List<Product>) session.getAttribute("products");
                if (null == receipts || receipts.isEmpty()) {
                    errorMessage = "Something went wrong....";
                    request.setAttribute("errorMessage", errorMessage);
                    LOGGER.error("errorMessage --> " + errorMessage);
                    return forward;
                }
                receipts.stream().forEach(receipt -> {
                    receipt.setStatus(Statuses.SUCCESS.shortValue());
                    receipt.setProcessingTime(new Date().getTime());
                });
                receiptsDao.insertReceipts(receipts);

                for (Product product : products) {
                    productsDao.updateCountForProduct(product);
                }

                session.setAttribute("products", null);
                session.setAttribute("basket", null);
                session.setAttribute("total_price", 0.00);
                forward = Path.SUCCESS;
                return forward;
            } else if (request.getParameter("command").equals("edit_receipt")) {
                if (userRole == Role.HIGH_CASHIER || userRole == Role.MANAGER) {
                    Integer receiptId = Integer.parseInt(request.getParameter("edit_receipt"));
                    List<Receipt> receiptsForEdit = receiptsDao.findAllByReceiptId(receiptId);
                    request.setAttribute("receipt_for_editing", receiptsForEdit);
                    forward = Path.PAGE_EDIT_RECEIPT;
                    LOGGER.debug("Command finished");
                    return forward;
                } else {
                    errorMessage = "User don't have permissions";
                    request.setAttribute("errorMessage", errorMessage);
                    LOGGER.error("errorMessage --> " + errorMessage);
                    return forward;
                }
            } else if (request.getParameter("command").equals("cancel_product_from_receipt")) {
                if (userRole == Role.HIGH_CASHIER || userRole == Role.MANAGER) {
                    Integer id = Integer.parseInt(request.getParameter("cancel_product_id"));
                    Integer count = Integer.parseInt(request.getParameter("cancel_product_count"));
                    Receipt receipt = receiptsDao.findById(id);
                    Product product = productsDao.getProductsById(receipt.getProductID());
                    if (receiptsDao.removeProductFromReceipt(id)) {
                        product.setCount(product.getCount() + count);
                        if (!productsDao.updateCountForProduct(product)) {
                            throw new Exception("Error while cancel receipt. Id: " + id);
                        }
                    } else {
                        throw new Exception("Error while cancel receipt. Id: " + id);
                    }
                    forward = Path.SUCCESS;
                    return forward;
                } else {
                    errorMessage = "User don't have permissions";
                    request.setAttribute("errorMessage", errorMessage);
                    LOGGER.error("errorMessage --> " + errorMessage);
                    return forward;
                }
            } else if (request.getParameter("command").equals("cancel_receipt")) {
                if (userRole == Role.HIGH_CASHIER || userRole == Role.MANAGER) {
                    Receipt receipt = new Receipt();
                    Integer cancelReceiptId = Integer.parseInt(request.getParameter("cancel_receipt_id"));
                    User user = (User) session.getAttribute("user");
                    List<Receipt> receiptsForCancelation = receiptsDao.findAllSuccessByReceiptId(cancelReceiptId);
                    if (null == receiptsForCancelation || receiptsForCancelation.isEmpty()) {
                        errorMessage = new StringBuilder().append("Receipt № ").append(cancelReceiptId).append(" has already canceled").toString();
                        request.setAttribute("errorMessage", errorMessage);
                        LOGGER.error("errorMessage --> " + errorMessage);
                        return forward;
                    }

                    receipt.setReceiptId(cancelReceiptId);
                    receipt.setCancelTime(new Date().getTime());
                    receipt.setCancelUserID(user.getId());

                    if (receiptsDao.cancelReceipt(receipt)) {
                        for (Receipt r : receiptsForCancelation) {
                            Product product = productsDao.getProductsById(r.getProductID());
                            product.setCount(product.getCount() + r.getCount());
                            if (!productsDao.updateCountForProduct(product)) {
                            }
                        }
                    } else {
                        throw new Exception("Error while cancel receipt. Id: " + cancelReceiptId);
                    }

                    forward = Path.SUCCESS;
                    return forward;
                } else {
                    errorMessage = "User don't have permissions";
                    request.setAttribute("errorMessage", errorMessage);
                    LOGGER.error("errorMessage --> " + errorMessage);
                    return forward;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "Something went wrong....";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error(e);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        forward = Path.PAGE_PAYMENTS;


        LOGGER.debug("Command finished");
        return forward;
    }

}
