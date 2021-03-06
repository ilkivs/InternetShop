package db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String LOCALHOST_URL = "jdbc:mysql://localhost:3306/";
    private static final String PARAMS = "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "1111";
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class);

    public static Connection getConnection(String dbName) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.debug("Driver class connected");
            String url = LOCALHOST_URL + dbName + PARAMS;
            connection = DriverManager.getConnection(url, USER, PASSWORD);
            LOGGER.debug("Connection completed");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Can`t find Driver class", e);
        } catch (SQLException e) {
            LOGGER.error("Can`t connect to database", e);
        }
        return connection;
    }
}
