package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.protocol.TotalReceipt;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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

            for (int i = 0; i <= count; i++) {
                list.add(i + 1);
            }

            request.setAttribute("count", list);
        } else {
            Integer page = null;
            if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
                page = 0;
            else
                page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

            DecimalFormat formatter = new DecimalFormat("#####0.00");

            List<Receipt> receipts = receiptsDao.findAllReceipts(page);
            List<TotalReceipt> totalReceipts = new LinkedList<>();



            for (Receipt receipt : receipts) {

//                if (totalReceipts.isEmpty()) {
                    TotalReceipt tr = new TotalReceipt();
                    tr.setId(receipt.getReceiptId());
                    tr.setTotalAmount(formatter.format((receipt.getPrice() * receipt.getCount()) / 100.0));
                    tr.setLogin(receipt.getUserLogin());
                    tr.setActive(null == receipt.getCancelTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    tr.setDate(sdf.format(new Date(receipt.getProcessingTime())));
                    totalReceipts.add(tr);
//                } else {
//
//                    for (int i = 0; i < totalReceipts.size(); i++){
////                    for (TotalReceipt totalReceipt : totalReceipts) {
//
//                        if (totalReceipts.get(i).getId() == receipt.getId()) {
//                            TotalReceipt tr = totalReceipts.get(i);
////                            tr.setTotalAmount(formatter.format(tr.getTotalAmount() + ((receipt.getPrice() * receipt.getCount()) / 100.0)));
//                            totalReceipts.add(tr);
//                        } else {
//                            TotalReceipt tr = new TotalReceipt();
//                            tr.setId(receipt.getId());
//                            tr.setTotalAmount(formatter.format((receipt.getPrice() * receipt.getCount()) / 100.0));
//                            tr.setLogin(receipt.getUserLogin());
//                            tr.setActive(null == receipt.getCancelTime());
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                            tr.setDate(sdf.format(new Date(receipt.getProcessingTime())));
//                            totalReceipts.add(tr);
//                        }
//
//                    }
//                }

            }

            request.setAttribute("total_receipts", totalReceipts);
            request.setAttribute("receipts", receipts);

            request.setAttribute("payments", receipts);
            Long count = totalReceipts.stream().count() / 20;

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
