package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Product;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public class ProductsDaoImpl extends GenericDao {

    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(ProductsDaoImpl.class);

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO products VALUES (DEFAULT ,? ,? ,?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_PRESENT_PRODUCTS = "SELECT * FROM products WHERE count != 0 AND active = true";
    private static final String SQL_FIND_PRODUCT_BY_ID = "SELECT * FROM products WHERE count != 0 AND active = true AND id = ?";
    private static final String SQL_FIND_PRODUCT_BY_CATEGORY_ID = "SELECT * FROM products WHERE count != 0 AND active = true AND categories_id = ?";
    private static final String SQL_FIND_PRODUCT_BY_NAME = "SELECT * FROM products WHERE name = ?";
    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String SQL_FIND_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM products WHERE categories_id = ?";
    private static final String SQL_CHANGE_PRICE_FOR_PRODUCT = "UPDATE products SET price=? WHERE name=?";
    private static final String SQL_CHANGE_COUNT_FOR_PRODUCT = "UPDATE products SET count=? WHERE name=?";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE products SET count=?, price=? WHERE id=?";
    private static final String SQL_CHANGE_WEIGHT_FOR_PRODUCT = "UPDATE products SET weight=? WHERE name=?";

    public boolean insertProduct(Product products) {

        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, true);
            ps.setString(2, products.getName());
            ps.setLong(3, products.getPrice());
            ps.setLong(4, products.getWeight());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, products.getCategoriesId());
            ps.setInt(7, products.getCount());

            if (ps.executeUpdate() != 1)
                return false;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idField = rs.getInt(1);
                products.setId(idField);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return false;
        } finally {
            close(rs);

            try {
                LOCK.unlock();
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
        return true;
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        ResultSet rs = null;
        List<Product> products = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCT_BY_CATEGORY_ID)) {
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                products.add(initProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return products;
    }

    public Product getProductsById(Integer id) {
        ResultSet rs = null;
        Product product = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = initProduct(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return product;
    }

    public Product getProductsByName(String name) {
        ResultSet rs = null;
        Product product = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCT_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = initProduct(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return product;
    }

    public List<Product> findAllProducts() {
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             Statement ps = connection.createStatement()) {
            rs = ps.executeQuery(SQL_FIND_ALL_PRODUCTS);
            while (rs.next()) {
                products.add(initProduct(rs));
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return Collections.emptyList();
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return products;
    }

    public List<Product> findAllPresentByName(String name) {
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCT_BY_NAME)) {
            ps.setString(1, name);
            while (rs.next()) {
                products.add(initProduct(rs));
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return Collections.emptyList();
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return products;
    }

    public List<Product> findAllPresentProducts() {
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             Statement ps = connection.createStatement()) {
            rs = ps.executeQuery(SQL_FIND_ALL_PRESENT_PRODUCTS);
            while (rs.next()) {
                products.add(initProduct(rs));
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return Collections.emptyList();
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return products;
    }

    public List<Product> findProductsByCategory(Integer categoryId) {
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCTS_BY_CATEGORY_ID)) {
            ps.setInt(1, categoryId);
            while (rs.next()) {
                products.add(initProduct(rs));
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return Collections.emptyList();
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return products;
    }

    public boolean updatePriceForProduct(Product product) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CHANGE_PRICE_FOR_PRODUCT)) {

            ps.setLong(1, product.getPrice());
            ps.setString(2, product.getName());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update product:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }

    public boolean updateProduct(Product product) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_PRODUCT)) {

            ps.setLong(1, product.getCount());
            ps.setLong(2, product.getPrice());
            ps.setInt(3, product.getId());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update product:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }

    public boolean updateCountForProduct(Product product) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CHANGE_COUNT_FOR_PRODUCT)) {

            ps.setLong(1, product.getCount());
            ps.setString(2, product.getName());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update product:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }

    public boolean updateWightForProduct(Product product) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CHANGE_WEIGHT_FOR_PRODUCT)) {

            ps.setLong(1, product.getWeight());
            ps.setString(2, product.getName());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update product:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }

    private Product initProduct(ResultSet rs) throws SQLException{
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setActive(rs.getBoolean("active"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getLong("price"));
        product.setWeight(rs.getLong("weight"));
        product.setDateOfAdding(rs.getDate("date_of_adding").getTime());
        product.setCategoriesId(rs.getInt("categories_id"));
        product.setCount(rs.getInt("count"));
        return product;
    }

}
