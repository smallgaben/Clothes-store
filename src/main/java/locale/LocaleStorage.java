package locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Storing and getting locale in different ways
 */
public interface LocaleStorage {
    Locale getLocale(HttpServletRequest req);

    void setLocale(HttpServletRequest req, HttpServletResponse resp, Locale locale);
}
