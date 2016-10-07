package model.service;

import exception.dao.DAOException;
import exception.service.ServiceException;
import model.dao.OrderDAO;
import model.dao.OrderItemDAO;
import model.domain.Order;
import model.domain.OrderItem;
import model.transaction.Query;
import model.transaction.TransactionManager;
import org.apache.log4j.Logger;

public class OrderServiceImpl implements OrderService {
    public static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
    private OrderDAO orderDao;
    private OrderItemDAO orderItemDAO;
    private TransactionManager manager;

    public OrderServiceImpl(OrderDAO orderDao, OrderItemDAO orderItemDAO, TransactionManager manager) {
        this.orderDao = orderDao;
        this.orderItemDAO = orderItemDAO;
        this.manager = manager;
    }

    @Override
    public Order createOrder(Order order) throws ServiceException {
        try {
            return manager.doTransaction(new Query<Order>() {
                @Override
                public Order doQuery() throws DAOException {
                    Order dbOrder = orderDao.create(order);

                    for (OrderItem item : order.getItemsList()) {
                        orderItemDAO.create(item, dbOrder.getId());
                    }

                    return dbOrder;
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
