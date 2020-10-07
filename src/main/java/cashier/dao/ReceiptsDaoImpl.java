package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Receipt;
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
public class ReceiptsDaoImpl extends GenericDao {

    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(ReceiptsDaoImpl.class);

    private static final String SQL_INSERT_RECEIPT = "INSERT INTO receipts VALUES (DEFAULT ,? ,? ,?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_CANCEL_RECEIPT = "UPDATE receipts SET status = 212, cancel_time = ?, cancel_user_id = ? WHERE \"id\" = ?";
    private static final String SQL_FIND_MAX_RECEIPT_NO = "SELECT MAX(receipt_id) as max_num  FROM receipts";
    private static final String SQL_FIND_ALL_RECEIPTS = "SELECT rec.*, prod.\"name\" as product_name FROM receipts as " +
            "rec, products as prod WHERE rec.id_product = prod.id ORDER BY rec.id ASC LIMIT 20 OFFSET ?";

    private static final String SQL_FIND_ALL_BY_RECEIPT_ID = "SELECT rec.*, prod.\"name\" as product_name FROM receipts as " +
            "rec, products as prod WHERE rec.id_product = prod.id AND rec.receipt_id = ?";

    private static final String SQL_FIND_ALL_BY_USER_RECEIPTS = "SELECT rec.*, prod.\"name\" as product_name FROM receipts as " +
            "rec, products as prod WHERE rec.id_product = prod.id AND rec.user_id = ? ORDER BY rec.id ASC LIMIT 20 OFFSET ?";
    private static final String SQL_FIND_ALL_RECEIPTS_TODAY = "SELECT * FROM receipts WHERE execute_time>= ? AND execute_time <= ? AND status = 3 AND user_id = ?";
    private static final String SQL_FIND_ALL_RECEIPTS_COUNT = "SELECT count(*) AS total FROM receipts";
    private static final String SQL_FIND_ALL_USER_RECEIPTS_COUNT = "SELECT count(*) AS total FROM receipts WHERE cashier_id = ?";

    public boolean insertReceipts(List<Receipt> receipts) {
        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            for (Receipt receipt : receipts) {
                ps.setInt(1, receipt.getProductID());
                ps.setInt(2, receipt.getReceiptId());
                ps.setInt(3, receipt.getUserID());
                ps.setObject(4, null);
                ps.setInt(5, receipt.getCount());
                ps.setLong(6, receipt.getPrice());
                ps.setShort(7, receipt.getStatus());
                ps.setTimestamp(8, new Timestamp(receipt.getProcessingTime()));
                ps.setTimestamp(9, null);

                if (ps.executeUpdate() != 1)
                    return false;
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idField = rs.getInt(1);
                    receipt.setId(idField);
                }
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

    public boolean cancelReceipt(Receipt receipt) {
        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CANCEL_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, receipt.getId());
            ps.setTimestamp(2, new Timestamp(receipt.getCancelTime()));
            ps.setInt(3, receipt.getCancelUserID());

            if (ps.executeUpdate() != 1)
                return false;
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

    public List<Receipt> findAllByReceiptId(Integer receiptId) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_BY_RECEIPT_ID)) {
            ps.setInt(1, receiptId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Receipt payment = new Receipt();
                payment.setId(rs.getInt("id"));
                payment.setProductID(rs.getInt("id_product"));
                payment.setUserID(rs.getInt("user_id"));
                payment.setCount(rs.getInt("count"));
                payment.setPrice(rs.getLong("price"));
                payment.setStatus(rs.getShort("status"));
                payment.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                payment.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                payment.setCancelUserID(rs.getInt("cancel_user_id"));
                payment.setProductName(rs.getString("product_name"));
                receipts.add(payment);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipts;
    }

    public List<Receipt> findAllUserReceipts(Integer userId, Integer offset) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_BY_USER_RECEIPTS)) {
            ps.setInt(1, userId);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                Receipt payment = new Receipt();
                payment.setId(rs.getInt("id"));
                payment.setProductID(rs.getInt("id_product"));
                payment.setUserID(rs.getInt("user_id"));
                payment.setCount(rs.getInt("count"));
                payment.setPrice(rs.getLong("price"));
                payment.setStatus(rs.getShort("status"));
                payment.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                payment.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                payment.setCancelUserID(rs.getInt("cancel_user_id"));
                payment.setProductName(rs.getString("product_name"));
                receipts.add(payment);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipts;
    }

    public Integer getLastReceiptNo() {
        ResultSet rs = null;
        Integer result = 0;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_MAX_RECEIPT_NO)) {
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("max_num");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return result;
    }

    public List<Receipt> findAllReceipts(Integer offset) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_RECEIPTS)) {
            ps.setInt(1, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                Receipt payment = new Receipt();
                payment.setId(rs.getInt("id"));
                payment.setProductID(rs.getInt("id_product"));
                payment.setUserID(rs.getInt("user_id"));
                payment.setCount(rs.getInt("count"));
                payment.setPrice(rs.getLong("price"));
                payment.setStatus(rs.getShort("status"));
                payment.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                payment.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                payment.setCancelUserID(rs.getInt("cancel_user_id"));
                payment.setProductName(rs.getString("product_name"));
                receipts.add(payment);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipts;
    }

    public List<Receipt> findAllReceiptsByCurrentDate(Timestamp startDate, Timestamp endDate, User user) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_RECEIPTS_TODAY)) {
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            ps.setInt(3, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Receipt payment = new Receipt();
                payment.setId(rs.getInt(1));
                payment.setProductID(rs.getInt(2));
                payment.setUserID(rs.getInt(3));
                payment.setCancelUserID(rs.getInt(4));
                payment.setCount(rs.getInt(5));
                payment.setPrice(rs.getLong(6));
                payment.setStatus(rs.getShort(7));
                payment.setProcessingTime(rs.getTimestamp(8).getTime());
                Timestamp cancelTime = rs.getTimestamp(9);
                payment.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                receipts.add(payment);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipts;
    }

    public Integer getAllReceiptsCount() {
        ResultSet rs = null;
        List<User> users = new LinkedList<>();
        Integer count = 0;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_RECEIPTS_COUNT)) {
            rs = ps.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return count;
    }

    public Integer getAllUserReceiptsCount(Integer userId) {
        ResultSet rs = null;
        List<User> users = new LinkedList<>();
        Integer count = 0;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_USER_RECEIPTS_COUNT)) {
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return count;
    }

//    public boolean updatePriceForProduct(Product product) {
//        LOCK.lock();
//        try (Connection connection = DataSourceConfig.getInstance().getConnection();
//             PreparedStatement ps = connection.prepareStatement(SQL_CHANGE_PRICE_FOR_PRODUCT)) {
//
//            ps.setLong(1, product.getPrice());
//            ps.setString(2, product.getName());
//            if (ps.executeUpdate() != 1) {
//                return false;
//            }
//        } catch (Exception e) {
//            LOGGER.error("Can't update product:" + e.getMessage());
//            return false;
//        } finally {
//            LOCK.unlock();
//        }
//        return true;
//    }
//
//    public boolean updateWightForProduct(Product product) {
//        LOCK.lock();
//        try (Connection connection = DataSourceConfig.getInstance().getConnection();
//             PreparedStatement ps = connection.prepareStatement(SQL_CHANGE_WEIGHT_FOR_PRODUCT)) {
//
//            ps.setLong(1, product.getWeight());
//            ps.setString(2, product.getName());
//            if (ps.executeUpdate() != 1) {
//                return false;
//            }
//        } catch (Exception e) {
//            LOGGER.error("Can't update product:" + e.getMessage());
//            return false;
//        } finally {
//            LOCK.unlock();
//        }
//        return true;

}
