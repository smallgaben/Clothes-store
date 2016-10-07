package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationContext {
    private List<AbstractValidator> validators;

    public ValidationContext() {
        validators = new ArrayList<>();
    }

    public ValidationContext add(AbstractValidator validator) {
        validators.add(validator);
        return this;
    }

    public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<>();
        for (AbstractValidator validator : validators) {
            if (!validator.isValid()) {
                errors.put(validator.getFieldName(), validator.errorMessage());
            }
        }

        return errors;
    }
}
