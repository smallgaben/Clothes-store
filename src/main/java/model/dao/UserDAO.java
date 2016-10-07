package model.dao;

import exception.dao.DAOException;
import model.domain.User;

import java.util.Collection;

public interface UserDAO {
    User create(User user) throws DAOException;

    User find(long id) throws DAOException;

    User find(String username) throws DAOException;

    Collection<User> find() throws DAOException;

    void update(User user) throws DAOException;

    void delete(User user) throws DAOException;
}
