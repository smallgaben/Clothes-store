package exception.locale;


public class LocaleNotFoundException extends RuntimeException {
    public LocaleNotFoundException(String m) {
        super(m);
    }
}
