package locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class CookieLocaleStorage implements LocaleStorage {
    private int cookieAge;

    public CookieLocaleStorage(int cookieAge) {
        this.cookieAge = cookieAge;
    }

    @Override
    public Locale getLocale(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("locale")) {
                    return new Locale(cookie.getValue());
                }
            }
        }

        return null;
    }

    @Override
    public void setLocale(HttpServletRequest req, HttpServletResponse resp, Locale locale) {
        Cookie localeCookie = new Cookie("locale", locale.toString());
        localeCookie.setMaxAge(cookieAge);
        resp.addCookie(localeCookie);
    }
}
