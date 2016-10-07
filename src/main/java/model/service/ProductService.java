package model.service;

import exception.service.ServiceException;
import model.domain.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Set<String> findCategories() throws ServiceException;

    Set<String> findTypes() throws ServiceException;

    int countProducts() throws ServiceException;

    int countProducts(String filter) throws ServiceException;

    Set<String> findBrands() throws ServiceException;

    List<Product> getProducts() throws ServiceException;

    List<Product> getProducts(String filter) throws ServiceException;

    Product getProduct(int parsedId) throws ServiceException;
}
