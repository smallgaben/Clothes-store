package filter.l10n;

import exception.locale.LocaleNotFoundException;
import locale.LocaleStorage;
import locale.LocaleStorageFactory;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Filter that changes application language due to request parameter, default locales or locales from browser
 */
public class LocalizationFilter implements Filter {
    public static final Logger LOGGER = Logger.getLogger(LocalizationFilter.class);
    private LocaleStorage storage;
    private String defaultLocale;
    private String[] supportedLocales;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        storage = LocaleStorageFactory.getLocale((String) filterConfig.getServletContext().getAttribute("localeStorage"));
        defaultLocale = filterConfig.getInitParameter("DEFAULT_LOCALE");

        if (defaultLocale == null || defaultLocale.isEmpty()) {
            throw new LocaleNotFoundException("There is no default locale in descriptor");
        }

        String locales = filterConfig.getInitParameter("SUPPORTED_LOCALES");

        if (locales == null || locales.isEmpty()) {
            supportedLocales = new String[]{defaultLocale};
        } else {
            supportedLocales = locales.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Locale result = checkRequestLocale(req);

        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
            @Override
            public Locale getLocale() {
                return result;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return Collections.enumeration(new ArrayList<>(Collections.singletonList(result)));
            }
        };

        storage.setLocale(wrapper, resp, result);
        filterChain.doFilter(wrapper, resp);
    }

    private Locale checkRequestLocale(HttpServletRequest req) {
        String reqLang = req.getParameter("lang");

        if (reqLang != null && !reqLang.isEmpty()) {
            Enumeration<Locale> locales = req.getLocales();

            while (locales.hasMoreElements()) {
                Locale browserLocale = locales.nextElement();

                if (browserLocale.getLanguage().equals(reqLang)) {
                    return new Locale(reqLang);
                }
            }
        }

        return checkLocaleStorage(req);
    }

    private Locale checkLocaleStorage(HttpServletRequest req) {
        Locale locale = storage.getLocale(req);

        if (locale != null) {
            return locale;
        }

        return checkAppSupportedLocales(req);
    }

    private Locale checkAppSupportedLocales(HttpServletRequest req) {
        Enumeration<Locale> browserLocales = req.getLocales();

        if (browserLocales != null) {
            while (browserLocales.hasMoreElements()) {
                Locale browserLocale = browserLocales.nextElement();

                if (supportedLocales.length > 0) {
                    for (String supportedLocale : supportedLocales) {
                        if (browserLocale.equals(new Locale(supportedLocale))) {
                            return new Locale(supportedLocale);
                        }
                    }
                }
            }
        }

        return new Locale(defaultLocale);
    }

    @Override
    public void destroy() {

    }
}
