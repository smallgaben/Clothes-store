package model.dao.mysql;

import exception.dao.DAOException;
import model.DBUtil;
import model.connection.ConnectionHolder;
import model.dao.ProductDAO;
import model.domain.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MySqlProductDAOImpl implements ProductDAO {
    public static final Logger LOGGER = Logger.getLogger(MySqlProductDAOImpl.class);

    @Override
    public Product create(Product product) throws DAOException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO product(name, category, brand, price, type, image_path) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setString(3, product.getBrand());
            ps.setFloat(4, product.getPrice());
            ps.setString(5, product.getType());
            ps.setString(6, product.getImage_path());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                product.setId(id);
                return product;
            } else {
                throw new DAOException("No generated keys returned");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public Product find(long id) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM product WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Product product = executeProduct(resultSet);
                ps.close();
                return product;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return null;
    }


    @Override
    public Product find(String name) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM product WHERE name=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Product product = executeProduct(resultSet);
                ps.close();
                return product;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return null;
    }

    @Override
    public int count() throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT count(*) FROM product";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return 0;
    }

    @Override
    public int count(String filter) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT count(*) FROM product "+filter;
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return 0;
    }

    @Override
    public Collection<Product> findWithFilter(String filter) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM product " + filter;
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(executeProduct(resultSet));
            }

            return products;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public Collection<String> findAttributes(String attribute) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT DISTINCT " + attribute + " FROM product";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            Set<String> attributes = new HashSet<>();

            while (resultSet.next()) {
                attributes.add(resultSet.getString(1));
            }

            return attributes;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public Collection<Product> find() throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM product";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(executeProduct(resultSet));
            }

            return products;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void update(Product product) throws DAOException {
        PreparedStatement ps = null;
        String sql = "UPDATE product Set name=?, category=?, brand=?, price=?, type=?, image_path=? WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setString(3, product.getBrand());
            ps.setFloat(4, product.getPrice());
            ps.setString(5, product.getType());
            ps.setString(6, product.getImage_path());
            ps.setLong(7, product.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void delete(Product product) throws DAOException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM product WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    private Product executeProduct(ResultSet resultSet) throws DAOException, SQLException {
        Product product = new Product();

        product.setId(resultSet.getInt(1));
        product.setName(resultSet.getString(2));
        product.setCategory(resultSet.getString(3));
        product.setBrand(resultSet.getString(4));
        product.setPrice(resultSet.getFloat(5));
        product.setType(resultSet.getString(6));
        product.setImage_path(resultSet.getString(7));

        return product;
    }
}
