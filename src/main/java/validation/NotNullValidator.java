package validation;

public class NotNullValidator extends AbstractValidator {
    public static final String ERROR_MESSAGE = "Field can't be null: ";

    public NotNullValidator(String value, String fieldName) {
        super(value, fieldName);
    }

    @Override
    public boolean isValid() {
        return getValue() != null && !getValue().isEmpty();
    }

    @Override
    public String errorMessage() {
        return ERROR_MESSAGE;
    }
}
