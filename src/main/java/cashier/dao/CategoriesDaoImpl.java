package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Category;
import cashier.dao.entity.Receipt;
import org.apache.log4j.Logger;

import java.sql.*;
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
}
