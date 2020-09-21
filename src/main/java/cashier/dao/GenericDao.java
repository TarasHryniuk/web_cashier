package cashier.dao;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public abstract class GenericDao {

    private static final Logger LOGGER = Logger.getLogger(GenericDao.class);

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }
}
