package model.dao.mysql;

import exception.dao.DAOException;
import model.DBUtil;
import model.connection.ConnectionHolder;
import model.dao.UserDAO;
import model.domain.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySqlUserDaoImpl implements UserDAO {
    public static final Logger LOGGER = Logger.getLogger(MySqlUserDaoImpl.class);

    @Override
    public User create(User user) throws DAOException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO user(username,realname,surname,phonenum,email,password,image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRealname());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getPhonenum());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getImagePath());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                user.setId(id);
                return user;
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
    public User find(long id) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM user WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = executeUser(resultSet);
                ps.close();
                return user;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return null;
    }

    @Override
    public User find(String username) throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM user WHERE username=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = executeUser(resultSet);
                ps.close();
                return user;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }

        return null;
    }


    @Override
    public Collection<User> find() throws DAOException {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM user";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(executeUser(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        PreparedStatement ps = null;
        String sql = "UPDATE user Set username=?, realname=?, surname=?, phonenum=?, email=?, password=?, image_path=? WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRealname());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getPhonenum());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getImagePath());
            ps.setLong(8, user.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE id=?";
        try {
            Connection connection = ConnectionHolder.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DBUtil.close(ps);
        }
    }

    private User executeUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong(1));
        user.setUsername(resultSet.getString(2));
        user.setRealname(resultSet.getString(3));
        user.setSurname(resultSet.getString(4));
        user.setPhonenum(resultSet.getString(5));
        user.setEmail(resultSet.getString(6));
        user.setPassword(resultSet.getString(7));
        user.setImagePath(resultSet.getString(8));
        user.setRole(resultSet.getString(9));

        return user;
    }
}
