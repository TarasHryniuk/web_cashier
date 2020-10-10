import cashier.dao.CategoriesDaoImpl;
import cashier.dao.entity.Category;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
}
