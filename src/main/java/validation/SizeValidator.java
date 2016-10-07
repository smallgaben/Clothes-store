package validation;

public class SizeValidator extends AbstractValidator {
    public static final String ERROR_MESSAGE = "Field isn't enough long: ";
    private int size;

    public SizeValidator(String value, String fieldName, int size) {
        super(value, fieldName);
        this.size = size;
    }

    @Override
    public boolean isValid() {
        return getValue().length() > size;
    }

    @Override
    public String errorMessage() {
        return ERROR_MESSAGE;
    }
}
