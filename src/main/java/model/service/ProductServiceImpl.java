package model.service;

import exception.dao.DAOException;
import exception.service.ServiceException;
import model.dao.ProductDAO;
import model.domain.Product;
import model.transaction.Query;
import model.transaction.TransactionManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class ProductServiceImpl implements ProductService {
    public static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);
    private ProductDAO productDAO;
    private TransactionManager manager;

    public ProductServiceImpl(ProductDAO productDAO, TransactionManager manager) {
        this.productDAO = productDAO;
        this.manager = manager;
    }

    @Override
    public Set<String> findCategories() throws ServiceException {
        try {
            return manager.doTransaction(new Query<Set<String>>() {
                @Override
                public Set<String> doQuery() throws DAOException {
                    Set<String> categories = (Set<String>) productDAO.findAttributes("category");

                    if (categories.isEmpty()) {
                        throw new DAOException("No categories found");
                    }

                    return categories;
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> findTypes() throws ServiceException {
        try {
            return manager.doTransaction(new Query<Set<String>>() {
                @Override
                public Set<String> doQuery() throws DAOException {
                    Set<String> types = (Set<String>) productDAO.findAttributes("type");

                    if (types.isEmpty()) {
                        throw new DAOException("No types found");
                    }

                    return types;
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countProducts() throws ServiceException {
        try {
            return manager.doTransaction(new Query<Integer>() {
                @Override
                public Integer doQuery() throws DAOException {
                    return productDAO.count();
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countProducts(String filter) throws ServiceException {
        try {
            return manager.doTransaction(new Query<Integer>() {
                @Override
                public Integer doQuery() throws DAOException {
                    return productDAO.count(filter);
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> findBrands() throws ServiceException {
        try {
            return manager.doTransaction(new Query<Set<String>>() {
                @Override
                public Set<String> doQuery() throws DAOException {
                    Set<String> brands = (Set<String>) productDAO.findAttributes("brand");

                    if (brands.isEmpty()) {
                        throw new DAOException("No brands found");
                    }

                    return brands;
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> getProducts() throws ServiceException {
        try {
            return manager.doTransaction(new Query<List<Product>>() {
                @Override
                public List<Product> doQuery() throws DAOException {
                    return (List<Product>) productDAO.find();
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> getProducts(String filter) throws ServiceException {
        try {
            return manager.doTransaction(new Query<List<Product>>() {
                @Override
                public List<Product> doQuery() throws DAOException {
                    return (List<Product>) productDAO.findWithFilter(filter);
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Product getProduct(int parsedId) throws ServiceException {
        try {
            return manager.doTransaction(new Query<Product>() {
                @Override
                public Product doQuery() throws DAOException {
                    return productDAO.find(parsedId);
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
