package cashier.conf;

import java.sql.Connection;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Taras Hryniuk, created on  19.09.2020
 * email : hryniuk.t@gmail.com
 */

public class DataSourceConfig {

    private static final String STAGE = "ALL";
    private static final String CHANGELOG_FILE = "liquibase/master-change-log.xml";
    private static final Logger LOGGER = Logger.getLogger(DataSourceConfig.class);
    private static DataSourceConfig dataSourceConfig;
    private static DataSource dataSource;


    private DataSourceConfig() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/finalproject");
        } catch (NamingException e) {
            LOGGER.error(e);
        }

        liquibase();
    }

    private void liquibase() {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

            Liquibase liquiBase = new Liquibase(CHANGELOG_FILE, resourceAccessor, db);
            liquiBase.update(STAGE);
        } catch (SQLException | LiquibaseException e) {
            LOGGER.error(e);
        }
    }

    public static DataSourceConfig getInstance() {
        if (dataSourceConfig == null) {
            dataSourceConfig = new DataSourceConfig();
        }
        return dataSourceConfig;
    }


    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
