package model.dao.factory;

import model.dao.OrderDAO;
import model.dao.OrderItemDAO;
import model.dao.ProductDAO;
import model.dao.UserDAO;
import model.dao.inmemory.InMemoryUserDAOImpl;

public class InMemoryDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new InMemoryUserDAOImpl();
    }

    @Override
    public ProductDAO getProductDAO() {
        throw new UnsupportedOperationException("InMemory doesn't have local storage for products");
    }

    @Override
    public OrderDAO getOrderDAO() {
        throw new UnsupportedOperationException("InMemory doesn't have local storage for orders");
    }

    @Override
    public OrderItemDAO getOrderItemDAO() {
        throw new UnsupportedOperationException("InMemory doesn't have local storage for order items");
    }
}
