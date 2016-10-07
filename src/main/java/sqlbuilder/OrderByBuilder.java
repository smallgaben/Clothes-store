package sqlbuilder;

import model.domain.Product;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class OrderByBuilder implements Builder {
    public static final Logger LOGGER = Logger.getLogger(OrderByBuilder.class);
    public static final String DEFAULT_SORT_FIELD = "name";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    private String fieldName;
    private String direction;

    public OrderByBuilder(String fieldName, String direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDirection() {
        return direction;
    }

    public String build() {
        if (!checkArgument(fieldName) && !checkArgument(direction)) {
            StringBuilder builder = new StringBuilder();

            Direction parsedDirection;

            try {
                parsedDirection = Direction.valueOf(direction);
                checkFieldNameExist();
            } catch (IllegalArgumentException e) {
                LOGGER.error(e.getMessage());
                parsedDirection = Direction.valueOf(DEFAULT_SORT_DIRECTION);
                this.fieldName = DEFAULT_SORT_FIELD;
            }

            builder.append("ORDER BY ").append(fieldName).append(" ").append(parsedDirection);
            return builder.toString();
        } else {
            return "ORDER BY " + DEFAULT_SORT_FIELD + " " + DEFAULT_SORT_DIRECTION;
        }
    }

    private boolean checkArgument(String fieldName) {
        return fieldName == null || fieldName.isEmpty();
    }

    private void checkFieldNameExist() {
        Class product = Product.class;

        for (Field field : product.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return;
            }
        }

        throw new IllegalArgumentException("Field with this name doesn't exist");
    }
}
