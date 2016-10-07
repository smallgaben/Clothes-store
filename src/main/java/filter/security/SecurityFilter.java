package filter.security;

import model.domain.User;
import model.domain.security.Constraint;
import model.service.UserService;
import org.apache.log4j.Logger;
import parser.security.SecurityParser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    public static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
    private SecurityParser parser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        parser = (SecurityParser) filterConfig.getServletContext().getAttribute("securityParser");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        parser.parseXmlToObject();

        int result = checkUriInSecurityList(req, resp);


        if (result != 0) {
            chain.doFilter(req, response);
        }
    }

    private int checkUriInSecurityList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Constraint constraint : parser.getSecurity().getConstraints()) {
            if (req.getRequestURI().matches(constraint.getUrlPattern())) {
                LOGGER.debug("uri matched to pattern");
                return checkLoginUser(req, resp);
            }
        }

        return 1;
    }

    private int checkLoginUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            LOGGER.debug("Redirecting to login page");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return 0;
        }

        return checkRoleAccess(req, resp);
    }

    private int checkRoleAccess(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uri = req.getRequestURI();
        User user = (User) req.getSession().getAttribute("user");

        for (Constraint constraint : parser.getSecurity().getConstraints()) {
            if (uri.matches(constraint.getUrlPattern()) && user.getRole().equals(constraint.getRole())) {
                return 1;
            }
        }

        LOGGER.debug("Role " + user.getRole() + " doesn't have access to requested uri " + uri);
        resp.sendRedirect("accessDenied.jsp");
        return 0;
    }

    @Override
    public void destroy() {

    }
}
