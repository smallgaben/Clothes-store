package validation;

import java.util.Objects;

public class EqualsValidator extends AbstractValidator {
    private String confirmValue;
    public static final String ERROR_MESSAGE = "Field doesn't equal confirm field: ";

    public EqualsValidator(String value, String fieldName, String confirmValue) {
        super(value, fieldName);
        this.confirmValue = confirmValue;
    }

    @Override
    public boolean isValid() {
        Objects.requireNonNull(confirmValue, "Value for confirming: " + getFieldName() + " is null");
        return Objects.equals(confirmValue, getValue());
    }

    @Override
    public String errorMessage() {
        return ERROR_MESSAGE;
    }
}
