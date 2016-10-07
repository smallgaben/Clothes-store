package model.service;

import exception.service.ServiceException;
import model.domain.Order;

public interface OrderService {
    Order createOrder(Order order) throws ServiceException;
}
