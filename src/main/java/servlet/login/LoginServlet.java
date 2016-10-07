package servlet.login;

import exception.service.ServiceException;
import model.domain.User;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        HttpSession session = req.getSession();
        UserService service = (UserService) req.getServletContext().getAttribute("userService");

        try {
            User existingUser = service.readUser(user);

            if (existingUser == null) {
                session.setAttribute("loginErrorMessage", "User doesn't exist");
                resp.sendRedirect("login.jsp");
                return;
            }

            session.setAttribute("user", existingUser);

            resp.sendRedirect(req.getHeader("referer"));
            session.removeAttribute("badval");
        } catch (ServiceException e) {
            LOGGER.warn(e);
        }
    }
}
