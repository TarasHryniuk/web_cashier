package cashier.dao;

import cashier.conf.DataSourceConfig;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Taras Hryniuk, created on  21.09.2020
 * email : hryniuk.t@gmail.com
 */
public class UserDaoImpl extends GenericDao {

    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT ,? ,? ,?, ?, ?, ?)";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users LIMIT 20 OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_COUNT = "SELECT count(*) AS total FROM users";
    private static final String SQL_BLOCK_USER_BY_LOGIN = "UPDATE users SET active=? WHERE id=?";

    public boolean insertUser(User user) throws SQLException {
        ResultSet rs = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, true);
            ps.setObject(2, user.getTerminalId());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getAuthCode());
            ps.setString(5, user.getFullName());
            ps.setInt(6, user.getRole());

            if (ps.executeUpdate() != 1)
                return false;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idField = rs.getInt(1);
                user.setId(idField);
            }
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

    public User getUserByLogin(String login) {
        ResultSet rs = null;
        User user = null;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setActive(rs.getBoolean("active"));
                user.setTerminalId(rs.getInt("terminal_id"));
                user.setLogin(rs.getString("login"));
                user.setAuthCode(rs.getString("auth_code"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getInt("role"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return user;
    }

    public List<User> getAllUsers(Integer offset) {
        ResultSet rs = null;
        List<User> users = new LinkedList<>();
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            ps.setInt(1, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setActive(rs.getBoolean("active"));
                user.setTerminalId(rs.getInt("terminal_id"));
                user.setLogin(rs.getString("login"));
                user.setAuthCode(rs.getString("auth_code"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getInt("role"));
                user.setRoleName(Role.getRole(user).getName());
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return users;
    }

    public Integer getAllUsersCount() {
        ResultSet rs = null;
        List<User> users = new LinkedList<>();
        Integer count = 0;
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_USERS_COUNT)) {
            rs = ps.executeQuery();
            if(rs.next())
                count = rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            close(rs);
            LOCK.unlock();
        }
        return count;
    }

    public boolean blockUserByLogin(User user) {
        LOCK.lock();
        try (Connection connection = DataSourceConfig.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_BLOCK_USER_BY_LOGIN)) {

            ps.setBoolean(1, false);
            ps.setString(2, user.getLogin());
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Can't update user:" + e.getMessage());
            return false;
        } finally {
            LOCK.unlock();
        }
        return true;
    }
}
