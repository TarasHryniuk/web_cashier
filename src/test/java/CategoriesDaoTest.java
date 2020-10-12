import cashier.conf.DataSourceConfig;
import cashier.dao.CategoriesDaoImpl;
import cashier.dao.UserDaoImpl;
import cashier.dao.entity.Category;
import cashier.dao.entity.User;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.assertNotEquals;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class CategoriesDaoTest {

    @Spy //actually this annotation is not necessary here
    private static DataSourceConfig dbManager;

    @Mock
    private CategoriesDaoImpl categoriesDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testFindById() {
        MockitoAnnotations.initMocks(this);
        categoriesDao.getCategoryByName("category");
        Mockito.verify(categoriesDao).getCategoryByName("category");
    }

    @Test
    public void test() {
        MockitoAnnotations.initMocks(this);
        categoriesDao.getAllCategories();
        Mockito.verify(categoriesDao).getAllCategories();
    }

    @Test
    public void testInsert() {
        MockitoAnnotations.initMocks(this);
        Category category = new Category();
        category.setId(1);
        category.setName("name");
        categoriesDao.insertCategory(category);
        Mockito.verify(categoriesDao).insertCategory(category);
    }

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String URL_CONNECTION = "jdbc:h2:~/test;user=youruser;password=yourpassword;";
    private static final String USER = "youruser";
    private static final String PASS = "yourpassword";

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        try (OutputStream output = new FileOutputStream("app.properties")) {
            Properties prop = new Properties();
            prop.setProperty("connection.url", URL_CONNECTION);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }

        dbManager = DataSourceConfig.getInstance();

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "  id INTEGER(11) NOT NULL AUTO_INCREMENT,\n" +
                    " login VARCHAR(20) NOT NULL, \n" +
                    "  PRIMARY KEY (id));";

            String sql1 = "CREATE TABLE IF NOT EXISTS teams (\n" +
                    "  id INTEGER(11) NOT NULL AUTO_INCREMENT,\n" +
                    " name VARCHAR(20) NOT NULL, \n" +
                    "  PRIMARY KEY (id));";

            String sql2 = "CREATE TABLE IF NOT EXISTS users_teams (  \n" +
                    "\n" +
                    " user_id INT REFERENCES users(id) ON DELETE CASCADE,  \n" +
                    "\n" +
                    " team_id INT REFERENCES teams(id) ON DELETE CASCADE,  \n" +
                    "\n" +
                    " UNIQUE (user_id, team_id) \n" +
                    "\n" +
                    ");";

            statement.executeUpdate(sql);
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        }
    }

//    @org.junit.Test
//    public void s1Test13dfgfg() throws Exception{
//        UserDaoImpl userDao = new UserDaoImpl();
//        User user = new User();
//        user.setLogin("aaa");
//        user.setAuthCode("aaa");
//        user.setFullName("aaa");
//        user.setRole(0);
//        assertNotEquals(false, userDao.insertUser(user));
//    }

}
