package cashier.servlet;

import cashier.dao.CategoriesDaoImpl;
import cashier.dao.ProductsDaoImpl;
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
public class GetProductsByCategory extends HttpServlet {


    private static final Logger LOGGER = Logger.getLogger(GetCategoriesServlet.class);
    private ProductsDaoImpl productsDao;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        productsDao = new ProductsDaoImpl();

        Integer categoryId = Integer.parseInt(request.getParameter("category_id"));

        String categoriesJsonString = new Gson().toJson(productsDao.getProductsByCategoryId(categoryId));

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(categoriesJsonString);
        out.flush();
    }

}
