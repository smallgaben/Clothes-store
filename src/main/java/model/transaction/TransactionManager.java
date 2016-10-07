package model.transaction;

import exception.dao.DAOException;
import model.DBUtil;
import model.connection.ConnectionHolder;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    public static final Logger LOGGER = Logger.getLogger(TransactionManager.class);
    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T doTransaction(Query<T> query) throws DAOException {
        Connection connection = null;
        T result = null;
        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ConnectionHolder.setConnection(connection);

            result = query.doQuery();
            connection.commit();

        } catch (DAOException daoEx) {
            try {
                LOGGER.error("DAO exception occurred: " + daoEx.getMessage() + "\n Trying to rollback operation");
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1);
            }

            throw new DAOException(daoEx, "Operation didn't execute");
        } catch (SQLException e) {
            throw new IllegalStateException("Troubles with connection: " + e.getMessage());
        } finally {
            ConnectionHolder.setConnection(null);
            DBUtil.close(connection);
        }

        return result;
    }
}
