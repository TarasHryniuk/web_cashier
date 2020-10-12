import cashier.dao.CategoriesDaoImpl;
import cashier.dao.UserDaoImpl;
import cashier.dao.entity.Category;
import cashier.dao.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class CategoriesDaoTest {

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
    public void getAllCategories() {
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

    @Test
    public void testToString() {
        Category category = new Category();
        category.setId(1);
        category.setName("name");

        assertEquals("Category{id=1, name='name'}", category.toString());
    }

    @Test
    public void testHashCode() {
        Category category = new Category();
        category.setId(1);
        category.setName("name");

        assertEquals(3374699, category.hashCode());
    }

    @Test
    public void testGetName() {
        Category category = new Category();
        category.setId(1);
        category.setName("name");

        assertEquals("name", category.getName());
    }

    @Test
    public void testGetId() {
        Category category = new Category();
        category.setId(1);
        category.setName("name");

        assertEquals(new Integer(1), category.getId());
    }

    @Test
    public void testEquals() {
        Category category = new Category();
        category.setId(1);
        category.setName("name");

        Category categorySecondary = new Category();
        categorySecondary.setId(1);
        categorySecondary.setName("name");

        assertEquals(true, category.equals(categorySecondary));
    }
}
