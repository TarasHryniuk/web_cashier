package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.protocol.TotalReceipt;
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
            Integer page;
            if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
                page = 0;
            else
                page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

            List<TotalReceipt> receipts = receiptsDao.findAllReceiptsGrouping(page, user.getId());

            request.setAttribute("total_receipts", receipts);
            request.setAttribute("receipts", receipts);

            request.setAttribute("payments", receipts);
            Long count = receipts.stream().count() / 20;

            List<Integer> list = new ArrayList<>();

            for (int i = 0; i <= count; i++) {
                list.add(i + 1);
            }

            request.setAttribute("count", list);
        } else {
            Integer page;
            if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
                page = 0;
            else
                page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

            List<TotalReceipt> receipts = receiptsDao.findAllReceiptsGrouping(page);

            request.setAttribute("total_receipts", receipts);
            request.setAttribute("receipts", receipts);

            request.setAttribute("payments", receipts);
            Long count = receipts.stream().count() / 20;

            System.out.println(count);

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
