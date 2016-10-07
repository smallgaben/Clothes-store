package validation;

import org.junit.Assert;
import org.junit.Test;

public class EqualsValidatorTest {
    private Validator validator;

    @Test
    public void testValidParameters() throws Exception {
        String value = "test text";
        String fieldName = "text";
        String confirmValue = "test text";
        validator = new EqualsValidator(value, fieldName, confirmValue);

        Assert.assertTrue(validator.isValid());
    }

    @Test
    public void testNotValidParameters() throws Exception {
        String value = "test text";
        String fieldName = "text";
        String confirmValue = "test text1";
        validator = new EqualsValidator(value, fieldName, confirmValue);

        Assert.assertFalse(validator.isValid());
    }

    @Test(expected = NullPointerException.class)
    public void testNullParameter() throws Exception {
        String value = "test text";
        String fieldName = "text";
        String confirmValue = null;
        validator = new EqualsValidator(value, fieldName, confirmValue);
        validator.isValid();
    }
}
