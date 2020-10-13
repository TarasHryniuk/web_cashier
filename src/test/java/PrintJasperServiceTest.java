import cashier.services.PrintJasperService;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;

/**
 * @author Taras Hryniuk, created on  13.10.2020
 * email : hryniuk.t@gmail.com
 */
public class PrintJasperServiceTest {

    @Test
    public void tesTotalReceiptGetProductID() {
        PrintJasperService printJasperService = new PrintJasperService();
        Map<String, Object> valuesMap = new HashMap<>();

        assertNotEquals("BufferedImage@7e6f74c: type = 1 DirectColorModel: rmask=ff0000 gmask=ff00 bmask=ff amask=0 IntegerInterleavedRaster: width = 1131 height = 1600 #Bands = 3 xOff = 0 yOff = 0 dataOffset[0] 0", ((BufferedImage) printJasperService.extractPrintImage(printJasperService.getJasperPrint(valuesMap, PrintJasperService.REPORT))).toString());
    }

}
