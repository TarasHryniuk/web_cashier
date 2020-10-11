package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Receipt;
import cashier.dao.entity.User;
import cashier.protocol.TotalReceipt;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    private static final String SQL_CANCEL_RECEIPT = "UPDATE receipts SET status = 212, cancel_time = ?, cancel_user_id = ? WHERE \"receipt_id\" = ?";
    private static final String SQL_CANCEL_RECEIPT_BY_ID = "UPDATE receipts SET status = 212, cancel_time = ?, cancel_user_id = ? WHERE \"id\" = ?";
    private static final String SQL_FIND_MAX_RECEIPT_NO = "SELECT MAX(receipt_id) as max_num  FROM receipts";

    private static final String SQL_FIND_ALL_RECEIPTS = "SELECT rec.*, prod.\"name\" as product_name, u.\"login\" as user_login " +
            "FROM receipts as rec JOIN users u ON rec.user_id = u.\"id\" JOIN products as prod ON rec.id_product = prod.id " +
            "ORDER BY rec.id DESC LIMIT 20 OFFSET ?";

    private static final String SQL_FIND_ALL_GROUPING = "SELECT receipt_id as receipt_id, \"sum\"(rec.price * rec.count) as sum, processing_time " +
            "as processing_time, u.\"login\" as login , status FROM receipts as rec JOIN users u ON rec.user_id = u.\"id\" " +
            "GROUP BY receipt_id, processing_time, user_id, u.\"login\", status ORDER BY rec.receipt_id DESC LIMIT 20 OFFSET ?";

    private static final String SQL_FIND_ALL_BY_USER_ID_GROUPING = "SELECT receipt_id as receipt_id, \"sum\"(rec.price * rec.count) as sum, processing_time " +
            "as processing_time, u.\"login\" as login , status FROM receipts as rec JOIN users u ON rec.user_id = u.\"id\" " +
            "GROUP BY receipt_id, processing_time, user_id, u.\"login\", status WHERE user_id = ? ORDER BY rec.receipt_id DESC LIMIT 20 OFFSET ?";

    private static final String SQL_FIND_BY_RECEIPT_ID = "SELECT rec.*, prod.\"name\" as product_name FROM receipts " +
            "as rec JOIN products as prod ON rec.id_product = prod.id AND rec.id = ?";


    private static final String SQL_FIND_ALL_BY_RECEIPT_ID = "SELECT rec.*, prod.\"name\" as product_name FROM receipts " +
            "as rec JOIN products as prod ON rec.id_product = prod.id AND rec.receipt_id = ?";

    private static final String SQL_FIND_ALL_SUCCESS_BY_RECEIPT_ID = "SELECT rec.*, prod.\"name\" as product_name FROM receipts " +
            "as rec JOIN products as prod ON rec.id_product = prod.id AND rec.receipt_id = ? AND status = 3";

    private static final String SQL_FIND_ALL_BY_USER_RECEIPTS = "SELECT rec.*, prod.\"name\" as product_name FROM receipts " +
            "as rec JOIN products as prod ON rec.id_product = prod.id WHERE rec.user_id = ? ORDER BY rec.id ASC LIMIT 20 OFFSET ?";

    private static final String SQL_FIND_RECEIPTS_TODAY = "SELECT receipt_id as receipt_id, \"sum\"(rec.price * rec.count) as sum, " +
            "processing_time as processing_time, status FROM receipts as rec WHERE processing_time >= CURRENT_DATE " +
            "AND processing_time <= CURRENT_DATE + INTERVAL '1 DAY' AND user_id = ? GROUP BY rec.receipt_id, " +
            "rec.processing_time, rec.cancel_time, rec.user_id, rec.status";

    private static final String SQL_FIND_ALL_RECEIPTS_COUNT = "SELECT count(*) AS total FROM receipts";
    private static final String SQL_FIND_ALL_USER_RECEIPTS_COUNT = "SELECT count(*) AS total FROM receipts WHERE cashier_id = ?";

    private static final String SQL_DELETE_PRODUCT_FROM_RECEIPT = "DELETE FROM receipts WHERE \"receipt_id\" = ? AND user_id = ?";

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

    public boolean cancelReceiptById(Receipt receipt) {
        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CANCEL_RECEIPT_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, new Timestamp(receipt.getCancelTime()));
            ps.setInt(2, receipt.getCancelUserID());
            ps.setInt(3, receipt.getId());

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

    public boolean deleteProductFromReceipt(Receipt receipt) {
        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_PRODUCT_FROM_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, new Timestamp(receipt.getCancelTime()));
            ps.setInt(2, receipt.getCancelUserID());
            ps.setInt(3, receipt.getReceiptId());

            if (ps.executeUpdate() == 0)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
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
            ps.setTimestamp(1, new Timestamp(receipt.getCancelTime()));
            ps.setInt(2, receipt.getCancelUserID());
            ps.setInt(3, receipt.getReceiptId());

            if (ps.executeUpdate() == 0)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
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

    public Receipt findById(Integer id) {
        ResultSet rs = null;
        Receipt receipt = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_RECEIPT_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProductID(rs.getInt("id_product"));
                receipt.setUserID(rs.getInt("user_id"));
                receipt.setCount(rs.getInt("count"));
                receipt.setPrice(rs.getLong("price"));
                receipt.setStatus(rs.getShort("status"));
                receipt.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                receipt.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                receipt.setCancelUserID(rs.getInt("cancel_user_id"));
                receipt.setProductName(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipt;
    }

    public Receipt findByIdProductIdCount(Integer id, Integer productId, Integer count) {
        ResultSet rs = null;
        Receipt receipt = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_RECEIPT_ID)) {
            ps.setInt(1, id);
            ps.setInt(2, productId);
            ps.setInt(3, count);
            rs = ps.executeQuery();
            while (rs.next()) {
                receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProductID(rs.getInt("id_product"));
                receipt.setUserID(rs.getInt("user_id"));
                receipt.setCount(rs.getInt("count"));
                receipt.setPrice(rs.getLong("price"));
                receipt.setStatus(rs.getShort("status"));
                receipt.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                receipt.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                receipt.setCancelUserID(rs.getInt("cancel_user_id"));
                receipt.setProductName(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipt;
    }

    public List<Receipt> findAllSuccessByReceiptId(Integer receiptId) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_SUCCESS_BY_RECEIPT_ID)) {
            ps.setInt(1, receiptId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Receipt payment = new Receipt();
                payment.setId(rs.getInt("id"));
                payment.setProductID(rs.getInt("id_product"));
                payment.setReceiptId(rs.getInt("receipt_id"));
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
                payment.setReceiptId(rs.getInt("receipt_id"));
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

    public List<TotalReceipt> findAllReceiptsGrouping(Integer offset, Integer userId) {
        ResultSet rs = null;
        List<TotalReceipt> totalReceipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_BY_USER_ID_GROUPING)) {
            ps.setInt(1, userId);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat formatter = new DecimalFormat("#####0.00");
            while (rs.next()) {

                TotalReceipt totalReceipt = new TotalReceipt();
                totalReceipt.setId(rs.getInt("receipt_id"));
                totalReceipt.setLogin(rs.getString("login"));
                totalReceipt.setStatus(rs.getShort("status"));
                Timestamp processingTime = rs.getTimestamp("processing_time");
                totalReceipt.setDate(sdf.format(processingTime));
                totalReceipt.setTotalAmount(formatter.format(rs.getLong("sum") / 100.0));

                totalReceipts.add(totalReceipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return totalReceipts;
    }

    public List<TotalReceipt> findAllReceiptsGrouping(Integer offset) {
        ResultSet rs = null;
        List<TotalReceipt> totalReceipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_GROUPING)) {
            ps.setInt(1, offset);
            rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat formatter = new DecimalFormat("#####0.00");
            while (rs.next()) {

                TotalReceipt totalReceipt = new TotalReceipt();
                totalReceipt.setId(rs.getInt("receipt_id"));
                totalReceipt.setLogin(rs.getString("login"));
                totalReceipt.setStatus(rs.getShort("status"));
                Timestamp processingTime = rs.getTimestamp("processing_time");
                totalReceipt.setDate(sdf.format(processingTime));
                totalReceipt.setTotalAmount(formatter.format(rs.getLong("sum") / 100.0));

                totalReceipts.add(totalReceipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return totalReceipts;
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
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProductID(rs.getInt("id_product"));
                receipt.setReceiptId(rs.getInt("receipt_id"));
                receipt.setUserID(rs.getInt("user_id"));
                receipt.setCount(rs.getInt("count"));
                receipt.setPrice(rs.getLong("price"));
                receipt.setStatus(rs.getShort("status"));
                receipt.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                receipt.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                receipt.setCancelUserID(rs.getInt("cancel_user_id"));
                receipt.setProductName(rs.getString("product_name"));
                receipt.setUserLogin(rs.getString("user_login"));
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return receipts;
    }

    public List<TotalReceipt> findAllTotalReceiptsByCurrentDate(User user) {
        ResultSet rs = null;
        List<TotalReceipt> totalReceipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_RECEIPTS_TODAY)) {
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat formatter = new DecimalFormat("#####0.00");

            while (rs.next()) {
                TotalReceipt totalReceipt = new TotalReceipt();

                totalReceipt.setId(rs.getInt("receipt_id"));
                Timestamp processingTime = rs.getTimestamp("processing_time");
                totalReceipt.setDate(sdf.format(processingTime));
                totalReceipt.setStatus(rs.getShort("status"));
                totalReceipt.setTotalAmount(formatter.format(rs.getLong("sum") / 100.0));

                totalReceipts.add(totalReceipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return totalReceipts;
    }

    public List<Receipt> findAllReceiptsByCurrentDate(User user) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_RECEIPTS_TODAY)) {
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProductID(rs.getInt("id_product"));
                receipt.setReceiptId(rs.getInt("receipt_id"));
                receipt.setUserID(rs.getInt("user_id"));
                receipt.setCancelUserID(rs.getInt("cancel_user_id"));
                receipt.setCount(rs.getInt("count"));
                receipt.setPrice(rs.getLong("price"));
                receipt.setStatus(rs.getShort("status"));
                receipt.setProcessingTime(rs.getTimestamp("processing_time").getTime());
                Timestamp cancelTime = rs.getTimestamp("cancel_time");
                receipt.setCancelTime(null != cancelTime ? cancelTime.getTime() : null);
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

}
