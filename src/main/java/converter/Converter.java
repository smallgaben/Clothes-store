package converter;

/**
 * Interface for converting from one bean class to another bean
 *
 * @param <T> From what convert class
 * @param <G> To  what convert class
 */
public interface Converter<T, G> {

    /**
     * Convert a an object T to a new object G
     *
     * @param storage contains fields to convert
     * @return G object with filled fields
     */
    G convert(T storage);
}
