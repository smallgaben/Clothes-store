package model.dao.inmemory;

import exception.dao.DAOException;
import model.dao.UserDAO;
import model.domain.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserDAOImpl implements UserDAO {
    Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public User find(long id) {
        return users.get(id);
    }

    @Override
    public User find(String username) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<User> find() {
        return users.values();
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }
}
