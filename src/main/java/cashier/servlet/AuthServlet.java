package cashier.servlet;

import cashier.dao.ProductsDaoImpl;
import cashier.dao.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Taras Hryniuk, created on  19.09.2020
 * email : hryniuk.t@gmail.com
 */
public class AuthServlet extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        message = "Hello World";

        ProductsDaoImpl productsDao = new ProductsDaoImpl();
//        CategoriesDaoImpl categoriesDao = new CategoriesDaoImpl();
//
//        Category category = new Category();
//        category.setName("Crypto");
//
//        System.out.println("Category insert: " + categoriesDao.insertCategory(category));

        Product products = new Product();

        products.setActive(true);
        products.setName("XRP_");
        products.setPrice(26L);
        products.setWeight(0L);
        products.setCategoriesId(1);

        System.out.println("Status insert: " + productsDao.insertProduct(products));

        System.out.println("Get by name: " + productsDao.getProductsByName("XRP"));

        System.out.println("Find all products: " + productsDao.findAllProducts());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");


    }

    public void destroy() {
        // do nothing.
    }

}
