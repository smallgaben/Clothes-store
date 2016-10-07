package servlet.basket;

import exception.service.ServiceException;
import model.domain.Product;
import model.service.ProductService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import servlet.bean.Basket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/basketService")
public class BasketServiceServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(BasketServiceServlet.class);
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Entered to basket service");
        Basket basket = (Basket) req.getSession().getAttribute("basket");

        if (basket == null) {
            basket = new Basket();
            req.getSession().setAttribute("basket", basket);
        }

        String condition = req.getParameter("condition");
        LOGGER.debug("condition is: " + condition);
        JSONObject result = new JSONObject();

        if (condition != null) {

            try {
                Product product = productService.getProduct(Integer.parseInt(req.getParameter("id")));
                result.put("product", product);

                switch (condition) {
                    case "setCount":
                        int setItemsCount = Integer.parseInt(req.getParameter("counter"));
                        basket.setProduct(product, setItemsCount);
                        result.put("productPrice", basket.countTotalPrice(product));
                        break;
                    case "add":
                        basket.addProduct(product);
                        break;
                    case "addCount":
                        int addItemsCount = Integer.parseInt(req.getParameter("counter"));
                        basket.addProduct(product, addItemsCount);
                        break;
                    case "delete":
                        basket.removeProduct(product);
                        break;
                }

                result.put("itemsCount", basket.countProducts());
                result.put("itemsPrice", basket.countTotalPrice());
                LOGGER.debug("Put into basket itemsNumber: " + basket.countProducts() + ", totalPrice: " + basket.countTotalPrice());

            } catch (ServiceException e) {
                LOGGER.warn("Basket didn't service condition: " + condition, e);
            } catch (NumberFormatException e) {
                LOGGER.error("Parameter didn't parsed: ", e);
            } catch (NullPointerException e) {
                LOGGER.warn("Somewhere occurred null: ", e);
            }
        }

        resp.setContentType("application/json");
        resp.getWriter().write(result.toString());
    }
}
