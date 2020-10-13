import cashier.conf.DataSourceConfig;
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

import java.sql.*;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Hryniuk, created on  09.10.2020
 * email : hryniuk.t@gmail.com
 */
public class ProductDaoTest {

    @Mock
    DataSourceConfig mockDataSource;

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPreparedStmnt;

    @Mock
    ResultSet mockResultSet;

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
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals("Products{id='15', active='true', name='name', price='15', weight='0', dateOfAdding='1', categoriesId='1', count='0'}", product.toString());
    }

    @Test
    public void testHashCode() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(596662523, product.hashCode());
    }

    @Test
    public void testGetName() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals("name", product.getName());
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

        assertEquals(new Integer(15), product.getId());
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

        assertEquals(new Long(0), product.getWeight());
    }

    @Test
    public void testGetDateOfAdding() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(new Long(1), product.getDateOfAdding());
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
    public void testisActive() {
        Product product = new Product();
        product.setCount(0);
        product.setCategoriesId(1);
        product.setWeight(0L);
        product.setPrice(15L);
        product.setId(15);
        product.setName("name");
        product.setActive(true);
        product.setDateOfAdding(1L);

        assertEquals(true, product.getActive());
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
