package model;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
    public static final Logger LOGGER = Logger.getLogger(DBUtil.class);

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Didn't close connection: " + e.getMessage());
        }
    }

    public static void close(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Can't close Prepared statement: " + e.getMessage());
        }
    }
}
