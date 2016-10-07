package validation;

import org.junit.Assert;
import org.junit.Test;

public class PatternValidatorTest {
    private Validator validator;

    @Test
    public void testValidatorRightValue() throws Exception {
        String value = "mirka040@ukr.net";
        String fieldName = "email";
        String emailPattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        validator = new PatternValidator(value, fieldName, emailPattern);

        Assert.assertTrue(validator.isValid());
    }

    @Test
    public void testValidatorNotValidCase() throws Exception {
        String value = "mirka040ukr.net";
        String fieldName = "email";
        String emailPattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        validator = new PatternValidator(value, fieldName, emailPattern);

        Assert.assertFalse(validator.isValid());
    }

    @Test(expected = NullPointerException.class)
    public void testNullDataCase() throws Exception {
        String value = null;
        String fieldName = "email";
        String emailPattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        validator = new PatternValidator(value, fieldName, emailPattern);

        Assert.assertFalse(validator.isValid());
    }
}
