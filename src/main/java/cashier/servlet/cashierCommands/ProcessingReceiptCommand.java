package cashier.servlet.cashierCommands;

import cashier.Path;
import cashier.Statuses;
import cashier.dao.ProductsDaoImpl;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.UserDaoImpl;
import cashier.dao.entity.Product;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.servlet.Command;
import cashier.util.StringHelpers;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  05.10.2020
 * email : hryniuk.t@gmail.com
 */
public class ProcessingReceiptCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(ProcessingReceiptCommand.class);
    private ReceiptsDaoImpl receiptsDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        try {
            receiptsDao = new ReceiptsDaoImpl();

            if (request.getParameter("command").equals("pay_basket")) {
                List<Receipt> receipts = (List<Receipt>) session.getAttribute("basket");
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
            } else if (request.getParameter("command").equals("cancel_basket")) {
                Receipt receipt = (Receipt) session.getAttribute("cancel_receipt");

                User user = (User) session.getAttribute("user");
                receipt.setCancelTime(new Date().getTime());
                receipt.setCancelUserID(user.getId());
                receiptsDao.cancelReceipt(receipt);
            }

        } catch (Exception e) {
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
