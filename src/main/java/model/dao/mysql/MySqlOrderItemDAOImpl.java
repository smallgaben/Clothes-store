package model.dao.mysql;

import exception.dao.DAOException;
import model.DBUtil;
import model.connection.ConnectionHolder;
import model.dao.OrderItemDAO;
import model.domain.OrderItem;
import model.domain.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySqlOrderItemDAOImpl implements OrderItemDAO {
    public static final Logger LOGGER = Logger.getLogger(MySqlOrderItemDAOImpl.class);

    @Override
    public void create(OrderItem orderItem, long orderId) throws DAOException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO order_item(product_id, product_count, product_price, order_id) VALUES (?,?,?,?)";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, orderItem.getProduct().getId());
            ps.setInt(2, orderItem.getProductCount());
            ps.setBigDecimal(3, orderItem.getProductPrice());
            ps.setLong(4, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public Collection<OrderItem> find(long id) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM order_item " +
                "JOIN product ON product.id=order_item.product_id " +
                "WHERE order_item.order_id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            List<OrderItem> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(executeOrderItem(resultSet));
                return items;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return null;
    }


    @Override
    public Collection<OrderItem> find() throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM order_item";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<OrderItem> orderItems = new ArrayList<>();

            while (resultSet.next()) {
                orderItems.add(executeOrderItem(resultSet));
            }

            return orderItems;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM order_item WHERE order_id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    private OrderItem executeOrderItem(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(6));
        product.setName(resultSet.getString(7));
        product.setCategory(resultSet.getString(8));
        product.setBrand(resultSet.getString(9));
        product.setPrice(resultSet.getFloat(10));
        product.setType(resultSet.getString(11));
        product.setImage_path(resultSet.getString(12));

        return new OrderItem(new Product(product), resultSet.getInt(3), resultSet.getBigDecimal(4));
    }
}
