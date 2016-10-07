package validation;

public interface Validator {
    boolean isValid();

    String errorMessage();
}
