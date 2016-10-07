package filter.l10n;

import exception.locale.LocaleNotFoundException;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Locale;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class LocalizationFilterTest {
    private LocalizationFilter filter;
    private FilterConfig config;
    private ServletContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private HttpSession session;

    @Before
    public void init() {
        session = mock(HttpSession.class);
        filter = new LocalizationFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        config = mock(FilterConfig.class);
        context = mock(ServletContext.class);
        chain = mock(FilterChain.class);
        when(request.getSession()).thenReturn(session);
        when(config.getServletContext()).thenReturn(context);
        when(context.getAttribute("localeStorage")).thenReturn("session");
    }

    @Test(expected = LocaleNotFoundException.class)
    public void shouldThrowExceptionWhenEmptyDefaultValue() throws Exception {
        filter.init(config);
    }

    @Test
    public void shouldSetLocaleFromLangParameter() throws Exception {
        when(config.getInitParameter("DEFAULT_LOCALE")).thenReturn("ru");
        when(request.getParameter("lang")).thenReturn("ru");
        when(request.getLocales()).thenReturn(Collections.enumeration(Collections.singletonList(new Locale("ru"))));

        filter.init(config);

        filter.doFilter(request, response, chain);

        verify(session).setAttribute(eq("locale"), eq(new Locale("ru")));
    }

    @Test
    public void shouldSetLocaleFromLocaleStorage() throws Exception {
        Locale testLocation = new Locale("UA");

        when(config.getInitParameter("DEFAULT_LOCALE")).thenReturn("ru");
        when(session.getAttribute("locale")).thenReturn(testLocation);

        filter.init(config);

        filter.doFilter(request, response, chain);

        verify(session).setAttribute(eq("locale"), eq(testLocation));
    }

    @Test
    public void shouldSetDefaultLocaleWhenBrowserDoesNotHaveLocales() throws Exception {
        Locale testLocale = new Locale("TEST");
        when(config.getInitParameter("DEFAULT_LOCALE")).thenReturn("ru");
        when(config.getInitParameter("SUPPORTED_LOCALES")).thenReturn(testLocale.toString() + ",ru,ua");

        filter.init(config);

        filter.doFilter(request, response, chain);

        verify(session).setAttribute(eq("locale"), eq(new Locale("ru")));
    }

    @Test
    public void shouldSetDefaultLocaleWhenAppLocalesAreEmpty() throws Exception {
        Locale testLocale = new Locale("test");
        when(config.getInitParameter("DEFAULT_LOCALE")).thenReturn(testLocale.toString());

        filter.init(config);

        filter.doFilter(request, response, chain);

        verify(session).setAttribute(eq("locale"), eq(testLocale));
    }
}
