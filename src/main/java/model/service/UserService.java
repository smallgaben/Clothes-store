package model.service;

import exception.service.ServiceException;
import model.domain.User;

public interface UserService {
    User createUser(User user) throws ServiceException;

    User readUser(User user) throws ServiceException;
}
