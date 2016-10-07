package model.dao;

import exception.dao.DAOException;
import model.domain.Order;

import java.util.Collection;

public interface OrderDAO {
    Order create(Order order) throws DAOException;

    Order find(long id) throws DAOException;

    Collection<Order> find() throws DAOException;

    void update(Order order) throws DAOException;

    void delete(Order order) throws DAOException;
}
