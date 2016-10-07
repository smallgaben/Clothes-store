package locale;

import listener.ContextListener;

import java.util.Objects;

public class LocaleStorageFactory {
    public static LocaleStorage getLocale(String value) {
        Objects.requireNonNull(value);

        switch (value) {
            case "session":
                return new SessionLocaleStorage();
            case "cookie":
                return new CookieLocaleStorage(ContextListener.cookieLocaleAge);
            default:
                throw new IllegalArgumentException("There is storage for this argument: " + value);
        }
    }
}
