import cashier.servlet.GetProductById;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

/**
 * @author Taras Hryniuk, created on  14.10.2020
 * email : hryniuk.t@gmail.com
 */
public class GetProductByIdTest extends Mockito {

//    String result = "{\n" +
//            "    \"id\": 1,\n" +
//            "    \"active\": true,\n" +
//            "    \"name\": \"xrp\",\n" +
//            "    \"price\": 25,\n" +
//            "    \"weight\": 0,\n" +
//            "    \"dateOfAdding\": 1602363600000,\n" +
//            "    \"categoriesId\": 1,\n" +
//            "    \"count\": 98\n" +
//            "}";
//
//
//    @Test
//    public void testServlet() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//
//        when(request.getParameter("id")).thenReturn("1");
//
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        new GetProductById().doGet(request, response);
//
//        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
//        writer.flush(); // it may not have been flushed yet...
//        assertTrue(stringWriter.toString().equals(result));
//    }

}
