package model.service;

import exception.service.ServiceException;
import model.dao.UserDAO;
import model.dao.inmemory.InMemoryUserDAOImpl;
import model.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UserServiceTest {
    private UserDAO userDAO;
    private UserService userService;
    private User user;

    @Before
    public void initData() {
        user = new User();
        userDAO = new InMemoryUserDAOImpl();
        user.setUsername("Palchik");
        user.setRealname("Misha");
        user.setSurname("Puzan");
        user.setPhonenum("123-123-1234");
        user.setEmail("puzan@gmail.com");
        user.setPassword("Puzanchik228");
    }

    @Test
    public void testCreateUser() throws Exception {
        userService.createUser(user);

        Assert.assertEquals(userDAO.find().size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testAlreadyExists() throws Exception {
        userService.createUser(user);
        userService.createUser(user);
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyArgument() throws Exception {
        user = null;
        userService.createUser(user);
    }
}
