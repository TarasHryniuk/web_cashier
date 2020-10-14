package cashier.servlet;

import cashier.Path;
import cashier.services.PrintJasperService;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class PrintAnalyticsCommand extends Command {

    private PrintJasperService printJasperService;
    private static final Logger LOGGER = Logger.getLogger(PrintAnalyticsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        String forward = Path.PAGE_ERROR_PAGE;
        String errorMessage = null;

        String file = null;
        if(request.getParameter("command").equals("all_products_analytics")){
            file = PrintJasperService.ALL_PRODUCTS_ANALYTICS;
        } else if(request.getParameter("command").equals("analytics_by_cashier_per_day")){
            file = PrintJasperService.ANALYTICS_BY_CASHIER_PER_DAY;
        }

        try {
            printJasperService = new PrintJasperService();
            response.setContentType("image/jpeg");
            OutputStream out = response.getOutputStream();
            ImageIO.write((BufferedImage) printJasperService.getJasperPrintAnalytics(file), "jpg", out);
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
