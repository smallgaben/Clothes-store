package locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SessionLocaleStorage implements LocaleStorage {
    @Override
    public Locale getLocale(HttpServletRequest req) {
        Locale locale = (Locale) req.getSession().getAttribute("locale");

        return locale == null ? null : locale;
    }

    @Override
    public void setLocale(HttpServletRequest req, HttpServletResponse resp, Locale locale) {
        req.getSession().setAttribute("locale", locale);
    }
}
