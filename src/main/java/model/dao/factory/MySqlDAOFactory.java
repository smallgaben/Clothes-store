package model.dao.factory;

import model.dao.OrderDAO;
import model.dao.OrderItemDAO;
import model.dao.ProductDAO;
import model.dao.UserDAO;
import model.dao.mysql.MySqlOrderDAOImpl;
import model.dao.mysql.MySqlOrderItemDAOImpl;
import model.dao.mysql.MySqlProductDAOImpl;
import model.dao.mysql.MySqlUserDaoImpl;

public class MySqlDAOFactory extends DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return new MySqlUserDaoImpl();
    }

    @Override
    public ProductDAO getProductDAO() {
        return new MySqlProductDAOImpl();
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new MySqlOrderDAOImpl();
    }

    @Override
    public OrderItemDAO getOrderItemDAO() {
        return new MySqlOrderItemDAOImpl();
    }
}
