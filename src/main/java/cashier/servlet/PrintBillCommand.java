package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.UserDaoImpl;
import cashier.dao.entity.Receipt;
import cashier.services.PrintJasperService;
import cashier.util.CalculateValuesByReceipts;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class PrintBillCommand extends Command {

    private PrintJasperService printJasperService;
    private static final Logger LOGGER = Logger.getLogger(PrintBillCommand.class);
    private Map<String, Object> valuesMap;
    private ReceiptsDaoImpl receiptsDao;
    private UserDaoImpl userDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        String forward = Path.PAGE_ERROR_PAGE;
        String errorMessage = null;

        DecimalFormat formatter = new DecimalFormat("#####0.00");

        try {
            receiptsDao = new ReceiptsDaoImpl();
            userDao = new UserDaoImpl();
//            Integer receiptId = Integer.parseInt(request.getParameter("print_bill"));
            Integer receiptId = 16;

            List<Receipt> receipts = receiptsDao.findAllByReceiptId(receiptId);

            valuesMap = new HashMap<>();
            valuesMap.put("{total.amount}", formatter.format(CalculateValuesByReceipts.getTotalSuccessReceiptsAmount(receipts) / 100.0));
            valuesMap.put("{bill.id}", receiptId.toString());

            StringBuilder sb = new StringBuilder();
            for (Receipt receipt : receipts) {
                if(receipt.getCancelTime() == null)
                    sb.append("\n").append("\t").append("\t").append("\t").append("Наіменування тавару: ").append(receipt.getProductName()).append(" ,кількість: ").append(receipt.getCount()).append(";");
            }
            valuesMap.put("{goods}", sb.toString());

            valuesMap.put("{cashier.name}", userDao.getUserById(receipts.get(0).getUserID()).getFullName());

            printJasperService = new PrintJasperService();
            response.setContentType("image/jpeg");
            OutputStream out = response.getOutputStream();
            ImageIO.write((BufferedImage) printJasperService.extractPrintImage(printJasperService.getJasperPrint(valuesMap, PrintJasperService.BILL)), "jpg", out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e);
            errorMessage = "Something went wrong....";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }
        forward = Path.PAGE_ALL_USERS;

        LOGGER.debug("Command finished");
        return forward;
    }

}
