package cashier.services;

import net.sf.jasperreports.engine.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class PrintJasperService {

    public static final String REPORT = File.separator + "reports" + File.separator + "document.jasper";
    public static final String BILL = File.separator + "reports" + File.separator + "bill.jasper";

    private static final JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());

    private static final Logger LOGGER = Logger.getLogger(PrintJasperService.class);

    public JasperPrint getJasperPrint(Map<String, Object> valuesMap, String fileName) {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        JasperPrint print;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("map", valuesMap);

            LOGGER.info("valuesMap for printing: " + valuesMap.toString());
            print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());

            return print;
        } catch (JRException e) {
            LOGGER.error(e);
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return null;
    }

    public Image extractPrintImage(JasperPrint print) {
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
