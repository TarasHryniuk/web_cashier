package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.util.TotalReceiptAmount;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  04.10.2020
 * email : hryniuk.t@gmail.com
 */
public class DocumentsCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(DocumentsCommand.class);
    private ReceiptsDaoImpl receiptsDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();
        // error handler
        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        User user = (User) session.getAttribute("user");
        Role userRole = Role.getRole(user);
        LOGGER.trace("userRole --> " + userRole);

        if (userRole == Role.CASHIER) {
            errorMessage = "User don't have permissions";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        receiptsDao = new ReceiptsDaoImpl();

        List<Receipt> receipts = receiptsDao.findAllReceiptsByCurrentDate(user);
        request.setAttribute("total_success_amount", TotalReceiptAmount.getTotalSuccessReceiptsAmount(receipts) / 100.0);
        request.setAttribute("reports_total_count", TotalReceiptAmount.getTotalReceiptsCount(receipts));
        request.setAttribute("reports_success_count", TotalReceiptAmount.getTotalSuccessReceiptsCount(receipts));
        request.setAttribute("total_cancel_amount", TotalReceiptAmount.getTotalCancelReceiptsAmount(receipts) / 100.0);
        request.setAttribute("reports_cancel_count", TotalReceiptAmount.getTotalCancelledReceiptsCount(receipts));

        request.setAttribute("report_name", request.getParameter("command").equals("x_report") ? "X Звіт" : "Z Звіт");

        forward = Path.PAGE_DOCUMENTS;

        LOGGER.debug("Command finished");
        return forward;
    }

}
