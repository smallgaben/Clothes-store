package servlet.order;

import exception.service.ServiceException;
import model.domain.Order;
import model.domain.Status;
import model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/confirmOrder")
public class ConfirmOrderServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(ConfirmOrderServlet.class);
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");

        try {
            order.setStatus(Status.accepted);
            order = orderService.createOrder(order);
            LOGGER.debug("Created a new order: " + order);
            session.removeAttribute("basket");
            session.removeAttribute("order");
        } catch (ServiceException e) {
            LOGGER.warn("Can't create order: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        req.setAttribute("successOrder", order);
        req.getRequestDispatcher("/WEB-INF/successOrder.jsp").forward(req, resp);
    }
}
