package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Product;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
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

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO products VALUES (DEFAULT ,? ,? ,?, ?, ?, ?)";
    private static final String SQL_FIND_PRODUCT_BY_NAME = "SELECT * FROM products WHERE name=?";
    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String SQL_CHANGE_PRICE_FOR_PRODUCT = "UPDATE products SET price=? WHERE name=?";
    private static final String SQL_CHANGE_WEIGHT_FOR_PRODUCT = "UPDATE products SET weight=? WHERE name=?";

    public boolean insertProduct(Product products) {

        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, products.getActive());
            ps.setString(2, products.getName());
            ps.setLong(3, products.getPrice());
            ps.setLong(4, products.getWeight());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, products.getCategoriesId());

            if (ps.executeUpdate() != 1)
                return false;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idField = rs.getInt(1);
                products.setId(idField);
            }
            connection.commit();
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

    public Product getProductsByName(String name) {
        ResultSet rs = null;
        Product product = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCT_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setActive(rs.getBoolean("active"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getLong("price"));
                product.setWeight(rs.getLong("weight"));
                product.setDateOfAdding(rs.getDate("date_of_adding").getTime());
                product.setCategoriesId(rs.getInt("categories_id"));
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
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setActive(rs.getBoolean(2));
                product.setName(rs.getString(3));
                product.setPrice(rs.getLong(4));
                product.setWeight(rs.getLong(5));
                product.setDateOfAdding(rs.getTimestamp(6).getTime());
                product.setCategoriesId(rs.getInt(7));
                products.add(product);
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

}
