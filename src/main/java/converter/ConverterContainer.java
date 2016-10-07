package converter;

import exception.converter.ConvertException;
import exception.converter.ConverterNotFound;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains list of converters for whole application
 */
public class ConverterContainer {
    public static final Logger LOGGER = Logger.getLogger(ConverterContainer.class);
    public static List<Converter> converters = new ArrayList<>();

    /**
     * Method for converting storage from  class to another class, which is suitable for converter from list
     *
     * @param from    class from converting
     * @param to      class to converting
     * @param storage object with fields
     * @param <T>     type of 'from' object
     * @param <G>     type of 'to' object
     * @return Converted object or null if Converted not found
     * @throws ConvertException
     */
    public static <T, G> G convert(Class<T> from, Class<G> to, T storage) throws ConvertException {
        G result;
        try {
            Converter converter = findConverter(from, to);
            result = (G) converter.convert(storage);
        } catch (ConverterNotFound converterNotFound) {
            LOGGER.info(converterNotFound.getMessage());
            return null;
        }

        if (result == null) {
            throw new ConvertException("Can't convert value " + storage.toString());
        }

        return result;
    }

    /**
     * Looks for suitable converter for params
     *
     * @param from class from converting
     * @param to   class to converting
     * @param <T>  type of class 'from' converting
     * @param <G>  type of class 'to' converting
     * @return Suitable converter
     * @throws ConverterNotFound if can't find appropriate converter
     */
    private static <T, G> Converter findConverter(Class<T> from, Class<G> to) throws ConverterNotFound {
        for (Converter converter : converters) {
            Method convertMethod = converter.getClass().getMethods()[0];
            Class returnType = convertMethod.getReturnType();
            Class argumentType = convertMethod.getParameterTypes()[0];

            if (argumentType.getName().equals(from.getName()) && returnType.getName().equals(to.getName())) {
                return converter;
            }
        }

        throw new ConverterNotFound("Can't find converter");
    }
}
