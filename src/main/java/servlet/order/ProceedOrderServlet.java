package servlet.order;

import model.domain.*;
import org.apache.log4j.Logger;
import servlet.bean.Basket;
import validation.NotNullValidator;
import validation.ValidationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/proceedOrder")
public class ProceedOrderServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(ProceedOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("loginErrorMessage", "Log in for making orders");
            resp.sendRedirect("login.jsp");
            return;
        }

        Basket basket = (Basket) session.getAttribute("basket");

        if (basket == null) {
            session.setAttribute("basketErrorMessage", "Empty basket");
            resp.sendRedirect("checkout.jsp");
            return;
        }

        List<OrderItem> items = new ArrayList<>();
        for (Map.Entry<Product, Integer> item : basket.readItems().entrySet()) {
            items.add(new OrderItem(item.getKey(), item.getValue(), BigDecimal.valueOf(item.getKey().getPrice())));
        }

        Order order = new Order();
        order.setStatus(Status.forming);
        order.setUser(user);
        order.setItemsList(items);

        session.setAttribute("order", order);

        req.getRequestDispatcher("/WEB-INF/orderPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String address = req.getParameter("address");
        String creditcard = req.getParameter("creditCard");

        Map<String, String> errors = validateFields(req);
        if (!errors.isEmpty()) {
            session.setAttribute("errors", errors);
            resp.sendRedirect("/WEB-INF/orderPage.jsp");
            return;
        }

        Order order = (Order) session.getAttribute("order");
        order.setStatus(Status.accepted);
        order.setAddress(address);
        order.setCreditCard(creditcard);
        order.setTimestamp(new Timestamp(System.currentTimeMillis()));
        order.setDescription("Description");

        session.setAttribute("order", order);

        req.getRequestDispatcher("/WEB-INF/confirmPage.jsp").forward(req,resp);
    }

    private Map<String, String> validateFields(HttpServletRequest req) {
        ValidationContext validationContext = new ValidationContext();
        validationContext.add(new NotNullValidator(req.getParameter("address"), "adress"));
        validationContext.add(new NotNullValidator(req.getParameter("creditCard"), "creditCard"));

        return validationContext.validate();
    }
}
