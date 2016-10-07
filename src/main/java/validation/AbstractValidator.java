package validation;

import java.util.Objects;

public abstract class AbstractValidator implements Validator {
    private String value;
    private String fieldName;

    public AbstractValidator(String value, String fieldName) {
        this.value = value;
        this.fieldName = fieldName;
    }

    public String getValue() {
        Objects.requireNonNull(value, "Checked value: " + fieldName + " is null");
        return value;
    }

    public String getFieldName() {
        return fieldName;
    }
}
