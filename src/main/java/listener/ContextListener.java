package listener;

import converter.*;
import exception.descryptor.InitDescriptorException;
import model.dao.DAOType;
import model.dao.factory.DAOFactory;
import model.dao.factory.DAOTypeFactory;
import model.service.*;
import model.transaction.TransactionManager;
import org.apache.log4j.PropertyConfigurator;
import parser.security.SecurityParser;
import provider.CaptchaProvider;
import provider.ProviderFactory;
import servlet.captcha.CaptchaType;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static converter.ConverterContainer.converters;

@WebListener
public class ContextListener implements ServletContextListener {
    public static int cookieLocaleAge;
    private ScheduledExecutorService scheduler;
    private CaptchaProvider provider;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        initLog4j(servletContext);
        cookieLocaleAge = Integer.parseInt(servletContext.getInitParameter("COOKIE_LOCALE_TIME"));
        servletContext.setAttribute("rootDir", servletContext.getInitParameter("root_dir"));
        servletContext.setAttribute("localeStorage", servletContext.getInitParameter("LOCALE_STORAGE"));

        SecurityParser parser = setUpSecurityPathRoles(servletContext);
        servletContext.setAttribute("securityParser", parser);

        setCaptchaProvider(servletContext);

        try {
            setServices(servletContext);
        } catch (NamingException e) {
            throw new IllegalStateException(e);
        }

        fillInConverters();

        deleteOldCaptcha(provider);
    }

    private void setCaptchaProvider(ServletContext servletContext) {
        provider = ProviderFactory.getInstance(CaptchaType.valueOf(servletContext.getInitParameter("CAPTCHA_TYPE")));
        servletContext.setAttribute("provider", provider);
    }

    /**
     * initialize all necessary converters
     */
    private void fillInConverters() {
        converters.add(new FormBeanConverter());
        converters.add(new UserConverter());
        converters.add(new ProductFilterConverter());
        converters.add(new OrderByBuilderConverter());
        converters.add(new LimitBuilderConverter());
    }

    /**
     * Deletes old states of captchas from registration
     *
     * @param captchaCaptchaProvider storage of captcha instance
     */
    private void deleteOldCaptcha(CaptchaProvider captchaCaptchaProvider) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(captchaCaptchaProvider::removeOldCaptcha, 1, 1, TimeUnit.HOURS);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduler.shutdownNow();
    }

    /**
     * Initializes log4j configurations
     *
     * @param servletContext app context
     */
    private void initLog4j(ServletContext servletContext) {
        String log4jConfigFile = servletContext.getInitParameter("log4j-config-path");
        String fullPath = servletContext.getRealPath(log4jConfigFile);
        PropertyConfigurator.configure(fullPath);
    }

    /**
     * Initializes all the services
     *
     * @param servletContext app context
     * @throws NamingException
     */
    public void setServices(ServletContext servletContext) throws NamingException {
        String dbType = servletContext.getInitParameter("db-type");
        DAOFactory daoFactory = DAOTypeFactory.getFactory(DAOType.valueOf(dbType));

        InitialContext initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup(servletContext.getInitParameter("db-name"));

        TransactionManager manager = new TransactionManager(ds);

        UserService userService = new UserServiceImpl(daoFactory.getUserDAO(), manager);
        servletContext.setAttribute("userService", userService);

        ProductService productService = new ProductServiceImpl(daoFactory.getProductDAO(), manager);
        servletContext.setAttribute("productService", productService);

        OrderService orderService = new OrderServiceImpl(daoFactory.getOrderDAO(), daoFactory.getOrderItemDAO(), manager);
        servletContext.setAttribute("orderService", orderService);
    }

    public SecurityParser setUpSecurityPathRoles(ServletContext servletContext) {
        String path = servletContext.getInitParameter("security_path");
        String fullPath = servletContext.getRealPath(path);

        if (fullPath != null && !fullPath.isEmpty()) {
            return new SecurityParser(fullPath);
        } else {
            throw new InitDescriptorException("There is no security path in web.xml");
        }
    }
}
