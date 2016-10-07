package sqlbuilder;

public abstract class WhereBuilder implements Builder{
    protected void checkArgument(String argument) {
        if (argument == null || argument.isEmpty()) {
            throw new IllegalArgumentException("Filter name is empty");
        }
    }

    public abstract boolean isEmpty();
}
