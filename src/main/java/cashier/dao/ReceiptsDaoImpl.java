package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Receipt;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Taras Hryniuk, created on  21.09.2020
 * email : hryniuk.t@gmail.com
 */
public class ReceiptsDaoImpl extends GenericDao {

    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(ReceiptsDaoImpl.class);

    private static final String SQL_INSERT_RECEIPT = "INSERT INTO receipts VALUES (DEFAULT ,? ,? ,?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_EXECUTED_RECEIPT_TIME = "UPDATE receipts SET execute_time=? WHERE id=?";
    private static final String SQL_UPDATE_RECEIPT_STATUS = "UPDATE receipts SET execute_time=? WHERE id=?";
    private static final String SQL_FIND_ALL_RECEIPTS_BY_DATE = "SELECT * FROM receipts WHERE name=?";
    private static final String SQL_CHANGE_WEIGHT_FOR_PRODUCT = "UPDATE receipts SET weight=? WHERE name=?";

    public boolean insertReceipt(Receipt receipt) {

        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, receipt.getProductID());
            ps.setInt(2, receipt.getUserID());
            ps.setInt(3, receipt.getCount());
            ps.setLong(4, receipt.getPrice());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            if (ps.executeUpdate() != 1)
                return false;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idField = rs.getInt(1);
                receipt.setId(idField);
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

    public boolean updateReceiptExecuteTime(Receipt receipt) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_EXECUTED_RECEIPT_TIME)) {

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, receipt.getId());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update receipt:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }

    public boolean updateReceiptStatus(Receipt receipt) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_RECEIPT_STATUS)) {

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, receipt.getId());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update receipt:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }

}
