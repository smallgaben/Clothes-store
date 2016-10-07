package servlet.product;

import converter.ConverterContainer;
import exception.converter.ConvertException;
import exception.service.ServiceException;
import model.domain.Product;
import model.service.ProductService;
import org.apache.log4j.Logger;
import servlet.bean.ProductFilter;
import sqlbuilder.Builder;
import sqlbuilder.LimitBuilder;
import sqlbuilder.OrderByBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sqlbuilder.LimitBuilder.DEFAULT_PAGE;
import static sqlbuilder.LimitBuilder.DEFAULT_PAGINATION_SIZE;
import static sqlbuilder.OrderByBuilder.DEFAULT_SORT_DIRECTION;
import static sqlbuilder.OrderByBuilder.DEFAULT_SORT_FIELD;

@WebServlet("/listProducts")
public class ListIProductsServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(ListIProductsServlet.class);
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductFilter filter;
        Builder orderByBuilder;
        Builder limitBuilder;
        try {
            filter = ConverterContainer.convert(HttpServletRequest.class, ProductFilter.class, req);
            orderByBuilder = ConverterContainer.convert(HttpServletRequest.class, OrderByBuilder.class, req);
            limitBuilder = ConverterContainer.convert(HttpServletRequest.class, LimitBuilder.class, req);
        } catch (ConvertException e) {
            LOGGER.error(e.getMessage());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String orderBySql = orderByBuilder.build();
        String limitSql = limitBuilder.build();
        String whereSql = filter.getSqlExtending();

        try {
            List<Product> productList = productService.getProducts(whereSql + orderBySql + limitSql);
            LOGGER.debug("Built sql extending: " + whereSql + orderBySql + limitSql);

            req.setAttribute("products", productList);
            int productSize = productService.countProducts(whereSql);

            String pagination = ((LimitBuilder) limitBuilder).getLimit();

            try {
                int parsedPagination = Integer.parseInt(pagination);
                double pagesNum = Math.ceil(productSize / (double) parsedPagination);
                req.setAttribute("pagesNum", pagesNum);
            } catch (NumberFormatException | NullPointerException e) {
                LOGGER.warn("Can't parse pagination, set default");
                req.setAttribute("pagesNum", Math.ceil(productSize / (double) DEFAULT_PAGINATION_SIZE));
            }

            req.setAttribute("types", productService.findTypes());
            req.setAttribute("brands", productService.findBrands());
            req.setAttribute("categories", productService.findCategories());

            saveFiltersInputs(req);

        } catch (ServiceException e) {
            LOGGER.warn("Troubles with services: ", e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        req.getRequestDispatcher("WEB-INF/product.jsp").forward(req, resp);
    }

    private void saveFiltersInputs(HttpServletRequest req) {
        StringBuilder builder = new StringBuilder();

        String name = req.getParameter("name");
        if (name != null && !name.isEmpty()) {
            req.setAttribute("name", name);
            builder.append("name=").append(name).append("&");
        }

        String priceFrom = req.getParameter("priceFrom");
        if (priceFrom != null && !priceFrom.isEmpty()) {
            req.setAttribute("priceFrom", priceFrom);
            builder.append("priceFrom=").append(priceFrom).append("&");
        }

        String priceTo = req.getParameter("priceTo");
        if (priceTo != null && !priceTo.isEmpty()) {
            req.setAttribute("priceTo", priceTo);
            builder.append("priceTo=").append(priceTo).append("&");
        }

        String[] categories = req.getParameterValues("category");
        if (categories != null) {
            List<String> categoriesList = new ArrayList<>(Arrays.asList(categories));
            req.setAttribute("checked_categories", categoriesList);

            for (String category : categoriesList) {
                builder.append("category=").append(category).append("&");
            }
        }

        String[] brands = req.getParameterValues("brand");
        if (brands != null) {
            List<String> brandsList = new ArrayList<>(Arrays.asList(brands));
            req.setAttribute("checked_brands", brandsList);

            for (String brand : brands) {
                builder.append("brand=").append(brand).append("&");
            }
        }

        String[] types = req.getParameterValues("type");
        if (types != null) {
            List<String> typesList = new ArrayList<>(Arrays.asList(types));
            req.setAttribute("checked_types", typesList);

            for (String type : types) {
                builder.append("type=").append(type).append("&");
            }
        }

        String sortBy = req.getParameter("sortBy");
        if (sortBy != null && !sortBy.isEmpty()) {
            req.setAttribute("sortBy", sortBy);
            builder.append("sortBy=").append(sortBy).append("&");
        } else {
            req.setAttribute("sortBy", DEFAULT_SORT_FIELD + "_" + DEFAULT_SORT_DIRECTION);
            builder.append("sortBy=").append(DEFAULT_SORT_FIELD + "_" + DEFAULT_SORT_DIRECTION).append("&");
        }

        String paginationSize = req.getParameter("paginationSize");
        if (paginationSize != null && !paginationSize.isEmpty()) {
            req.setAttribute("paginationSize", paginationSize);
            builder.append("paginationSize=").append(paginationSize).append("&");
        } else {
            req.setAttribute("paginationSize", DEFAULT_PAGINATION_SIZE);
            builder.append("paginationSize=").append(DEFAULT_PAGINATION_SIZE).append("&");
        }

        String page = req.getParameter("page");
        if (page != null && !page.isEmpty()) {
            req.setAttribute("page", page);
        } else {
            req.setAttribute("page", DEFAULT_PAGE);
        }

        if (builder.length() > 0) {
            builder.delete(builder.length() - 1, builder.length());
        }

        req.setAttribute("queryString", builder.toString());
    }
}
