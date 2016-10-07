package model.dao.factory;

import model.dao.DAOType;

public class DAOTypeFactory {
    public static DAOFactory getFactory(DAOType type) {
        switch (type) {
            case MYSQL:
                return new MySqlDAOFactory();
            case INMEMORY:
                return new InMemoryDAOFactory();
            default:
                throw new IllegalArgumentException("There is no required factory");
        }
    }
}
