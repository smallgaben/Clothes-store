package servlet.registration;

import converter.FormBeanConverter;
import converter.UserConverter;
import model.dao.UserDAO;
import model.dao.inmemory.InMemoryUserDAOImpl;
import model.service.UserServiceImpl;
import nl.captcha.Captcha;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import provider.CaptchaProvider;
import provider.SessionCaptchaProvider;
import servlet.bean.CaptchaBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static converter.ConverterContainer.converters;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@Ignore
public class RegistrationIntegrationTest {
    private ServletContext context;
    private ServletConfig config;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserServiceImpl service;
    private CaptchaProvider captchaProvider;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private UserDAO userDAO;
    private Captcha captcha;
    private RegistrationServlet servlet = new RegistrationServlet();

    @Before
    public void initData() {

        converters.add(new FormBeanConverter());
        converters.add(new UserConverter());

        captcha = new Captcha.Builder(200, 50).addText().build();
        userDAO = new InMemoryUserDAOImpl();

        captchaProvider = new SessionCaptchaProvider();
        config = mock(ServletConfig.class);
        context = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(context.getAttribute("userService")).thenReturn(service);
        when(context.getAttribute("provider")).thenReturn(captchaProvider);
        when(config.getServletContext()).thenReturn(context);
        when(request.getServletContext()).thenReturn(context);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("register.jsp")).thenReturn(dispatcher);

        when(session.getAttribute("captcha")).thenReturn(new CaptchaBean("capthca", captcha));
    }

    @Test
    public void testValidInputUser() throws Exception {
        when(request.getParameter("username")).thenReturn("Mishka");
        when(request.getParameter("realname")).thenReturn("Misha");
        when(request.getParameter("surname")).thenReturn("Puzanchik");
        when(request.getParameter("phonenum")).thenReturn("123-123-1234");
        when(request.getParameter("email")).thenReturn("misha@mail.ru");
        when(request.getParameter("password")).thenReturn("Mishka040");
        when(request.getParameter("confirmpassword")).thenReturn("Mishka040");
        when(request.getParameter("captchaInput")).thenReturn(captcha.getAnswer());

        servlet.init(config);
        servlet.doGet(request, response);
        servlet.doPost(request, response);

        Assert.assertNotEquals(userDAO.find().size(), 0);
        verify(dispatcher).forward(request,response);
    }

    @Test
    public void testNotValidInput() throws Exception {
        when(request.getParameter("username")).thenReturn("Mishka");
        when(request.getParameter("realname")).thenReturn("Misha");
        when(request.getParameter("surname")).thenReturn("Puzanchik");
        when(request.getParameter("phonenum")).thenReturn("13-13-1234");
        when(request.getParameter("email")).thenReturn("mishamail.ru");
        when(request.getParameter("password")).thenReturn("Mishka040");
        when(request.getParameter("confirmpassword")).thenReturn("Ma040");
        when(request.getParameter("captchaInput")).thenReturn(captcha.getAnswer() + "1");

        Map<String, String> errors = new HashMap<>();
        errors.put("phonenum", "Field doesn't match regexp: ");
        errors.put("email", "Field doesn't match regexp: ");
        errors.put("captchaInput", "Field doesn't equal confirm field: ");
        errors.put("confirmpassword", "Field doesn't equal confirm field: ");
        servlet.init(config);
        servlet.doGet(request, response);
        servlet.doPost(request, response);

        verify(session).setAttribute(eq("captcha"), anyObject());
        verify(session).setAttribute(eq("inputers"), anyMap());
        verify(session).setAttribute("errors", errors);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testAlreadyExistingUser() throws Exception {
        when(request.getParameter("username")).thenReturn("Mishka");
        when(request.getParameter("realname")).thenReturn("Misha");
        when(request.getParameter("surname")).thenReturn("Puzanchik");
        when(request.getParameter("phonenum")).thenReturn("123-123-1234");
        when(request.getParameter("email")).thenReturn("misha@mail.ru");
        when(request.getParameter("password")).thenReturn("Mishka040");
        when(request.getParameter("confirmpassword")).thenReturn("Mishka040");
        when(request.getParameter("captchaInput")).thenReturn(captcha.getAnswer());

        servlet.init(config);
        servlet.doGet(request, response);
        servlet.doPost(request, response);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("existingError"), anyString());
        verify(dispatcher).forward(request, response);
    }
}
