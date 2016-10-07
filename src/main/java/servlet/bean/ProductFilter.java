package servlet.bean;

import org.apache.log4j.Logger;
import sqlbuilder.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

public class ProductFilter {
    public static final Logger LOGGER = Logger.getLogger(ProductFilter.class);
    private List<WhereBuilder> builders = new ArrayList<>();

    public void addFilter(WhereBuilder filter) {
        builders.add(filter);
    }

    public String getSqlExtending() {
        if (isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();

        builder.append("WHERE ");
        for (WhereBuilder whereBuilder : builders) {
            if (!whereBuilder.isEmpty()) {
                try {
                    builder.append(whereBuilder.build()).append(" AND ");
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Can't create sql extending: " + e.getMessage());
                    return "";
                }
            }
        }

        builder.delete(builder.length() - 4, builder.length());
        return builder.toString();
    }

    public boolean isEmpty() {
        for (WhereBuilder whereBuilder : builders) {
            if (!whereBuilder.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
