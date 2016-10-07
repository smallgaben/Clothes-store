package model.dao;

import exception.dao.DAOException;
import model.domain.Product;

import java.util.Collection;

public interface ProductDAO {
    Product create(Product product) throws DAOException;

    Product find(long id) throws DAOException;

    Product find(String name) throws DAOException;

    int count() throws DAOException;

    int count(String filter) throws DAOException;

    Collection<Product> findWithFilter(String filter) throws DAOException;

    Collection<String> findAttributes(String attribute) throws DAOException;

    Collection<Product> find() throws DAOException;

    void update(Product product) throws DAOException;

    void delete(Product product) throws DAOException;
}
