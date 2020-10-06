package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Category;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Taras Hryniuk, created on  21.09.2020
 * email : hryniuk.t@gmail.com
 */
public class CategoriesDaoImpl extends GenericDao{
    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(ProductsDaoImpl.class);

    private static final String SQL_INSERT_CATEGORIES = "INSERT INTO categories VALUES (DEFAULT ,?)";
    private static final String SQL_FIND_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE name=?";
    private static final String SQL_FIND_ALL_CATEGORIES = "SELECT * FROM categories ORDER BY id ASC";

    public boolean insertCategory(Category category) {
        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CATEGORIES, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());


            if (ps.executeUpdate() != 1)
                return false;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idField = rs.getInt(1);
                category.setId(idField);
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

    public Category getCategoryByName(String name) {
        ResultSet rs = null;
        Category category = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_CATEGORY_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return category;
    }

    public List<Category> getAllCategories() {
        ResultSet rs = null;
        List<Category> categories = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_CATEGORIES)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return categories;
    }
}
