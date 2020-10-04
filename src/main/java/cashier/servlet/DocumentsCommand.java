package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());

        List<Receipt> receipts = receiptsDao.findAllReceiptsByCurrentDate(startTime, endTime, user);

//        Long totalAmount = ;

        forward = Path.PAGE_DOCUMENTS;

        LOGGER.debug("Command finished");
        return forward;
    }

}
