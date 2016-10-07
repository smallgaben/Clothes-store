package model.dao;

import exception.dao.DAOException;
import model.domain.OrderItem;

import java.util.Collection;

public interface OrderItemDAO {
    void create(OrderItem orderItem, long orderId) throws DAOException;

    Collection<OrderItem> find(long id) throws DAOException;

    Collection<OrderItem> find() throws DAOException;

    void delete(long id) throws DAOException;
}
