package model.service;

import exception.dao.DAOException;
import exception.service.ServiceException;
import model.dao.UserDAO;
import model.domain.User;
import model.transaction.TransactionManager;
import org.apache.log4j.Logger;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    public static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;
    private TransactionManager manager;

    public UserServiceImpl(UserDAO userDAO, TransactionManager manager) {
        this.userDAO = userDAO;
        this.manager = manager;
    }

    @Override
    public synchronized User createUser(User user) throws ServiceException {
        Objects.requireNonNull(user);

        User existUser = readUser(user);

        if (existUser != null) {
            throw new ServiceException("User already exists");
        }

        try {
            return manager.doTransaction(() -> userDAO.create(user));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUser(User user) throws ServiceException {
        Objects.requireNonNull(user);

        try {
            return manager.doTransaction(() -> userDAO.find(user.getUsername()));
        } catch (DAOException e) {
            throw new ServiceException("User didn't read: " + e.getMessage());
        }
    }
}
