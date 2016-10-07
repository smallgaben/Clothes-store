package sqlbuilder;

public class BetweenBuilder extends WhereBuilder {
    private String filterName;
    private String priceFrom;
    private String priceTo;

    public BetweenBuilder(String filterName, String priceFrom, String priceTo) {
        this.filterName = filterName;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    @Override
    public String build() {
        checkArgument(filterName);

        if (isEmpty(priceFrom, priceTo)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        if (priceFrom == null || priceFrom.isEmpty()) {
            builder.append("(").append(filterName).append("<=").append(parsePrice(priceTo)).append(")");
            return builder.toString();
        }

        if (priceTo == null || priceTo.isEmpty()) {
            builder.append("(").append(filterName).append(">=").append(parsePrice(priceFrom)).append(")");
            return builder.toString();
        }

        float parsedFrom = parsePrice(priceFrom);
        float parsedTo = parsePrice(priceTo);

        builder.append("(").append(filterName).append(" BETWEEN ").append(parsedFrom).append(" AND ").append(parsedTo).append(")");
        return builder.toString();
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(priceFrom, priceTo);
    }

    private boolean isEmpty(String priceFrom, String priceTo) {
        return (priceFrom == null || priceFrom.isEmpty() && (priceTo == null || priceTo.isEmpty()));
    }

    private float parsePrice(String price) {
        try {
            return Float.parseFloat(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value can't be casted to price", e);
        }
    }
}
