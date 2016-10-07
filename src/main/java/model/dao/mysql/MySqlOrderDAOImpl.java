package model.dao.mysql;

import exception.dao.DAOException;
import model.DBUtil;
import model.connection.ConnectionHolder;
import model.dao.OrderDAO;
import model.domain.Order;
import model.domain.Status;
import model.domain.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySqlOrderDAOImpl implements OrderDAO {
    public static final Logger LOGGER = Logger.getLogger(MySqlOrderDAOImpl.class);

    @Override
    public Order create(Order order) throws DAOException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO `order`(status, description, date_time, user_id, address, creditcard) VALUES (?,?,?,?,?,?)";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getStatus().toString());
            ps.setString(2, order.getDescription());
            ps.setTimestamp(3, order.getTimestamp());
            ps.setLong(4, order.getUser().getId());
            ps.setString(5, order.getAddress());
            ps.setString(6, order.getCreditCard());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                order.setId(id);
                return order;
            } else {
                throw new DAOException("No generated keys returned");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public Order find(long id) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM `order`" +
                "JOIN user ON user.id=order.user_id" +
                "WHERE order.id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Order order = executeOrder(resultSet);
                ps.close();
                return order;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return null;
    }


    @Override
    public Collection<Order> find() throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM `order`";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<Order> orders = new ArrayList<>();

            while (resultSet.next()) {
                orders.add(executeOrder(resultSet));
            }

            return orders;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void update(Order order) throws DAOException {
        PreparedStatement ps = null;
        String sql = "UPDATE `order` Set status=?, description=?, date_time=?, user_id=?, address=?, creditcard=?  WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, order.getStatus().toString());
            ps.setString(2, order.getDescription());
            ps.setTimestamp(3, order.getTimestamp());
            ps.setLong(4, order.getUser().getId());
            ps.setString(5, order.getAddress());
            ps.setString(6, order.getCreditCard());
            ps.setLong(7, order.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void delete(Order order) throws DAOException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM `order` WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    private Order executeOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong(1));
        order.setStatus(Status.valueOf(resultSet.getString(2)));
        order.setDescription(resultSet.getString(3));
        order.setTimestamp(resultSet.getTimestamp(4));
        order.setAddress(resultSet.getString(5));
        order.setCreditCard(resultSet.getString(6));
        order.setUser(new User(resultSet.getLong(8),
                resultSet.getString(9),
                resultSet.getString(10),
                resultSet.getString(11),
                resultSet.getString(12),
                resultSet.getString(13),
                resultSet.getString(14),
                resultSet.getString(15)));

        return order;
    }
}
