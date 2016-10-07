package converter;

import sqlbuilder.OrderByBuilder;

import javax.servlet.http.HttpServletRequest;

import static sqlbuilder.OrderByBuilder.DEFAULT_SORT_DIRECTION;
import static sqlbuilder.OrderByBuilder.DEFAULT_SORT_FIELD;

public class OrderByBuilderConverter implements Converter<HttpServletRequest, OrderByBuilder> {
    @Override
    public OrderByBuilder convert(HttpServletRequest storage) {
        String orderBy = storage.getParameter("sortBy");

        if (orderBy != null && !orderBy.isEmpty()) {
            String fieldName = orderBy.split("_")[0];
            String direction = orderBy.split("_")[1];

            return new OrderByBuilder(fieldName, direction);
        } else {
            return new OrderByBuilder(DEFAULT_SORT_FIELD, DEFAULT_SORT_DIRECTION);
        }
    }
}
