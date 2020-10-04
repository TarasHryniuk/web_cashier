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

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO receipts VALUES (DEFAULT ,? ,? ,?, ?, ?, ?)";
    private static final String SQL_FIND_PRODUCT_BY_NAME = "SELECT * FROM receipts WHERE name=?";
    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT * FROM receipts ORDER BY id ASC LIMIT 20 OFFSET ?";
    private static final String SQL_FIND_ALL_PRODUCTS_TODAY = "SELECT * FROM receipts WHERE execute_time>= ? AND execute_time <= ? AND status = 3 AND user_id = ?";
    private static final String SQL_FIND_ALL_USER_PRODUCTS = "SELECT * FROM receipts WHERE cashier_id = ? LIMIT 20 OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_COUNT = "SELECT count(*) AS total FROM receipts";
    private static final String SQL_FIND_ALL_USER_PAYMENTS_COUNT = "SELECT count(*) AS total FROM receipts WHERE cashier_id = ?";
    private static final String SQL_CHANGE_PRICE_FOR_PRODUCT = "UPDATE receipts SET price=? WHERE name=?";
    private static final String SQL_CHANGE_WEIGHT_FOR_PRODUCT = "UPDATE receipts SET weight=? WHERE name=?";

//    public boolean insertProduct(Product products) {

//        ResultSet rs = null;
//        LOCK.lock();
//        try (Connection connection = DataSourceConfig.getInstance().getConnection();
//             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setBoolean(1, products.getActive());
//            ps.setString(2, products.getName());
//            ps.setLong(3, products.getPrice());
//            ps.setLong(4, products.getWeight());
//            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
//            ps.setInt(6, products.getCategoriesId());
//
//            if (ps.executeUpdate() != 1)
//                return false;
//            rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                int idField = rs.getInt(1);
//                products.setId(idField);
//            }
//        } catch (Exception e) {
//            LOGGER.error(e);
//            return false;
//        } finally {
//            close(rs);
//
//            try {
//                LOCK.unlock();
//            } catch (Exception e) {
//                LOGGER.error(e);
//            }
//        }
//        return true;
//    }

//    public Product getProductsByName(String name) {
//        ResultSet rs = null;
//        Product product = null;
//        LOCK.lock();
//        try (Connection connection = DataSourceConfig.getInstance().getConnection();
//             PreparedStatement ps = connection.prepareStatement(SQL_FIND_PRODUCT_BY_NAME)) {
//            ps.setString(1, name);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setActive(rs.getBoolean("active"));
//                product.setName(rs.getString("name"));
//                product.setPrice(rs.getLong("price"));
//                product.setWeight(rs.getLong("weight"));
//                product.setDateOfAdding(rs.getDate("date_of_adding").getTime());
//                product.setCategoriesId(rs.getInt("categories_id"));
//            }
//        } catch (SQLException e) {
//            LOGGER.error(e);
//        } finally {
//            close(rs);
//            LOCK.unlock();
//        }
//        return product;
//    }

    public List<Receipt> findAllUserReceipts(Integer userId, Integer offset) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS)) {
            ps.setInt(1, offset);
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

    public List<Receipt> findAllReceipts(Integer offset) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS)) {
            ps.setInt(1, offset);
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

    public List<Receipt> findAllReceiptsByCurrentDate(Timestamp startDate, Timestamp endDate, User user) {
        ResultSet rs = null;
        List<Receipt> receipts = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS_TODAY)) {
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
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_PAYMENTS_COUNT)) {
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
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_USER_PAYMENTS_COUNT)) {
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
