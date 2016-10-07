package validation;

import java.util.Objects;

public class PatternValidator extends AbstractValidator {
    private String regexp;
    public static final String ERROR_MESSAGE = "Field doesn't match regexp: ";

    public PatternValidator(String value, String fieldName, String regexp) {
        super(value, fieldName);
        this.regexp = regexp;
    }

    @Override
    public boolean isValid() {
        Objects.requireNonNull(regexp, "Regular expression for value: " + getFieldName() + " is empty");
        return getValue().matches(regexp);
    }

    @Override
    public String errorMessage() {
        return ERROR_MESSAGE;
    }
}
