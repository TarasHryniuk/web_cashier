import cashier.dao.ProductsDaoImpl;
import cashier.dao.entity.Category;
import cashier.dao.entity.Product;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class ProductDaoTest {

    @Mock
    private ProductsDaoImpl productsDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testFindById() {
        MockitoAnnotations.initMocks(this);
        productsDao.getProductsById(1);
        Mockito.verify(productsDao).getProductsById(1);
    }

    @Test
    public void getProductsByCategoryId() {
        MockitoAnnotations.initMocks(this);
        productsDao.getProductsByCategoryId(1);
        Mockito.verify(productsDao).getProductsByCategoryId(1);
    }

    @Test
    public void testInsert() {
        MockitoAnnotations.initMocks(this);
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);
        productsDao.insertProduct(product);
        Mockito.verify(productsDao).insertProduct(product);
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
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(new Long(15), product.getId());
    }

    @Test
    public void testGetPrice() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(new Long(15), product.getPrice());
    }

    @Test
    public void testGetWeight() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(new Integer(1), product.getCategoriesId());
    }

    @Test
    public void testGetCategory() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(new Integer(1), product.getCategoriesId());
    }

    @Test
    public void testGetCount() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(new Integer(0), product.getCount());
    }

    @Test
    public void testEquals() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        Product productSecondary = new Product();
        productSecondary.setCount(0);
        productSecondary.setCategoriesId(1);
        productSecondary.setWeight(0L);
        productSecondary.setPrice(15L);
        productSecondary.setId(15);
        productSecondary.setName("name");
        productSecondary.setActive(true);
        productSecondary.setDateOfAdding(1L);

        assertEquals(true, product.equals(productSecondary));
    }
}
