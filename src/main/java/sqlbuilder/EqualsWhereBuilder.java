package sqlbuilder;

public class EqualsWhereBuilder extends WhereBuilder {
    private String filterName;
    private String[] params;

    public EqualsWhereBuilder(String filterName, String[] params) {
        this.filterName = filterName;
        this.params = params;
    }

    @Override
    public String build() {
        checkArgument(filterName);

        StringBuilder builder = new StringBuilder();

        builder.append("(");
        for (String param : params) {
            if (param != null && !param.isEmpty()) {
                builder.append(filterName).append("=").append("'").append(param).append("'").append(" OR ");
            }
        }

        builder.delete(builder.length() - 4, builder.length());
        builder.append(")");

        return builder.toString();
    }

    @Override
    public boolean isEmpty() {
        if (params != null) {
            for (String param : params) {
                if (!param.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}
