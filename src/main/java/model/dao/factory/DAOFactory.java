package model.dao.factory;

import model.dao.OrderDAO;
import model.dao.OrderItemDAO;
import model.dao.ProductDAO;
import model.dao.UserDAO;

public abstract class DAOFactory {
    public abstract UserDAO getUserDAO();
    public abstract ProductDAO getProductDAO();
    public abstract OrderDAO getOrderDAO();
    public abstract OrderItemDAO getOrderItemDAO();
}
