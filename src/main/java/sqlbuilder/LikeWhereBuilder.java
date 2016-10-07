package sqlbuilder;

public class LikeWhereBuilder extends WhereBuilder {
    private String filterName;
    private String value;

    public LikeWhereBuilder(String filterName, String value) {
        this.filterName = filterName;
        this.value = value;
    }

    @Override
    public String build() {
        checkArgument(filterName);

        StringBuilder builder = new StringBuilder();

        builder.append("(").append(filterName).append(" LIKE ")
                .append("'").append("%").append(value).append("%").append("'").append(")");

        return builder.toString();
    }

    @Override
    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}
