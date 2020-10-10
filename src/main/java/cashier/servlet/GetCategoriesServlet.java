package cashier.servlet;

import cashier.dao.CategoriesDaoImpl;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Taras Hryniuk, created on  10.10.2020
 * email : hryniuk.t@gmail.com
 */
public class GetCategoriesServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetCategoriesServlet.class);
    private CategoriesDaoImpl categoriesDao;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        categoriesDao = new CategoriesDaoImpl();

        String categoriesJsonString = new Gson().toJson(categoriesDao.getAllCategories());

        System.out.println(categoriesJsonString);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(categoriesJsonString);
        out.flush();
    }
}
