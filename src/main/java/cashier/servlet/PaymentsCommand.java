package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  29.09.2020
 * email : hryniuk.t@gmail.com
 */
public class PaymentsCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(PaymentsCommand.class);
    private ReceiptsDaoImpl receiptsDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();
        String forward;

        User user = (User) session.getAttribute("user");
        Role userRole = Role.getRole(user);
        LOGGER.trace("userRole --> " + userRole);

        receiptsDao = new ReceiptsDaoImpl();

        if (userRole == Role.CASHIER || userRole == Role.HIGH_CASHIER) {
            Integer page = null;
            if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
                page = 0;
            else
                page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

            request.setAttribute("payments", receiptsDao.findAllUserReceipts(user.getId(), page));
            Integer count = receiptsDao.getAllUserReceiptsCount(user.getId()) / 20;

            List<Integer> list = new ArrayList<>();

            for (int i = 1; i <= count + 1; i++) {
                list.add(i);
            }

            request.setAttribute("count", list);
        } else {
            Integer page = null;
            if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
                page = 0;
            else
                page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

            request.setAttribute("payments", receiptsDao.findAllReceipts(page));
            Integer count = receiptsDao.getAllReceiptsCount() / 20;

            List<Integer> list = new ArrayList<>();

            for (int i = 0; i <= count; i++) {
                list.add(i + 1);
            }

            request.setAttribute("count", list);

        }

        forward = Path.PAGE_PAYMENTS;

        LOGGER.debug("Command finished");
        return forward;
    }
}
