package converter;

import servlet.bean.ProductFilter;
import sqlbuilder.BetweenBuilder;
import sqlbuilder.EqualsWhereBuilder;
import sqlbuilder.LikeWhereBuilder;

import javax.servlet.http.HttpServletRequest;

public class ProductFilterConverter implements Converter<HttpServletRequest, ProductFilter> {
    @Override
    public ProductFilter convert(HttpServletRequest storage) {
        ProductFilter filter = new ProductFilter();
        filter.addFilter(new EqualsWhereBuilder("category", storage.getParameterValues("category")));
        filter.addFilter(new EqualsWhereBuilder("type", storage.getParameterValues("type")));
        filter.addFilter(new EqualsWhereBuilder("brand", storage.getParameterValues("brand")));
        filter.addFilter(new LikeWhereBuilder("name", storage.getParameter("name")));
        filter.addFilter(new BetweenBuilder("price", storage.getParameter("priceFrom"), storage.getParameter("priceTo")));

        return filter;
    }
}
