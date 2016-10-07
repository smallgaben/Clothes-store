package servlet.product;

import exception.service.ServiceException;
import model.domain.Product;
import model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/singleProduct")
public class ShowSingleProduct extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(ShowSingleProduct.class);
    private ProductService productService;


    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");

        try {
            int parsedId = Integer.parseInt(productId);
            Product product = productService.getProduct(parsedId);

            req.setAttribute("product", product);
            req.setAttribute("types", productService.findTypes());
            req.setAttribute("brands", productService.findBrands());
            req.setAttribute("categories", productService.findCategories());
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.warn("Can't find product by id: ", e);
            req.setAttribute("error", "Can't find product");
        }

        req.getRequestDispatcher("WEB-INF/single.jsp").forward(req, resp);
    }
}
