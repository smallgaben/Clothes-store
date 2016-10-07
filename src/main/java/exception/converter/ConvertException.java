package exception.converter;

public class ConvertException extends Exception {
    public ConvertException(Throwable t){ super(t);}
    public ConvertException(String s) {
        super(s);
    }
}
