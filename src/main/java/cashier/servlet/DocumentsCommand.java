package cashier.servlet;

import cashier.Path;
import cashier.dao.ReceiptsDaoImpl;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.util.CalculateValuesByReceipts;
import net.sf.jasperreports.engine.*;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Taras Hryniuk, created on  04.10.2020
 * email : hryniuk.t@gmail.com
 */
public class DocumentsCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(DocumentsCommand.class);
    private ReceiptsDaoImpl receiptsDao;

    private static final JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());
    private Map<String, Object> valuesMap;

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

        valuesMap = new HashMap<>();
        valuesMap.put("total_success_amount", CalculateValuesByReceipts.getTotalSuccessReceiptsAmount(receipts) / 100.0);
        valuesMap.put("reports_total_count", CalculateValuesByReceipts.getTotalReceiptsCount(receipts));
        valuesMap.put("reports_success_count", CalculateValuesByReceipts.getTotalCancelReceiptsAmount(receipts) / 100.0);
        valuesMap.put("total_cancel_amount", CalculateValuesByReceipts.getTotalSuccessReceiptsAmount(receipts) / 100.0);
        valuesMap.put("reports_cancel_count", CalculateValuesByReceipts.getTotalCancelledReceiptsCount(receipts));
        valuesMap.put("report_name", request.getParameter("command").equals("x_report") ? "X Звіт" : "Z Звіт");


        if(request.getParameter("command").equals("x_report") || request.getParameter("command").equals("Z_report")) {
            response.setContentType("image/jpeg");
            OutputStream out = response.getOutputStream();
            ImageIO.write((BufferedImage) extractPrintImage(getJasperPrint()), "jpg", out);
            out.close();
        }

        forward = Path.PAGE_DOCUMENTS;

        LOGGER.debug("Command finished");
        return forward;
    }

    private JasperPrint getJasperPrint() {
        String fileName = File.separator + "reports" + File.separator + "final_project_document.jasper";
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        JasperPrint print;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("map", valuesMap);

            File f = new File(fileName);
            System.out.println("ClassLoader.getResource(): " + f.exists());
            print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());

            return print;
        } catch (JRException e) {
            LOGGER.error(e);
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return null;
    }

    private Image extractPrintImage(JasperPrint print) {
        Image rendered_image = null;
        try {

            rendered_image = (BufferedImage) printManager.printPageToImage(print, 0, 1.9f);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
        return rendered_image;
    }


}
