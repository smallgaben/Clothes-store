package sqlbuilder;

public class LimitBuilder implements Builder {
    public static final int DEFAULT_PAGINATION_SIZE = 2;
    public static final int DEFAULT_PAGE = 1;
    private String offset;
    private String limit;

    public LimitBuilder(String offset, String limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public String getLimit() {
        return limit;
    }

    public String build() {

        int parsedPagination = DEFAULT_PAGINATION_SIZE;
        int parsedPage = DEFAULT_PAGE;

        if (!checkArgument(offset) && !checkArgument(limit)) {
            try {
                parsedPagination = Integer.parseInt(limit);
                parsedPage = Integer.parseInt(offset);
            } catch (NumberFormatException e) {
                parsedPagination = DEFAULT_PAGINATION_SIZE;
                parsedPage = DEFAULT_PAGE;
            }

            if (parsedPagination <= 0 || parsedPage <= 0) {
                parsedPagination = DEFAULT_PAGINATION_SIZE;
                parsedPage = DEFAULT_PAGE;
            }
        }

        return " LIMIT " + ((parsedPage - 1) * parsedPagination) + "," + parsedPagination;
    }

    private boolean checkArgument(String fieldName) {
        return fieldName == null || fieldName.isEmpty();
    }
}
