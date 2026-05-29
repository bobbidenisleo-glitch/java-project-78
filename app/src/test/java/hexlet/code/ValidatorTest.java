package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator v;
    private StringSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.string();
    }

    @Test
    void testRequired() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("hello"));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hello"));
    }

    @Test
    void testMinLength() {
        schema.minLength(5);

        assertFalse(schema.isValid("abc"));
        assertTrue(schema.isValid("abcdef"));
        assertTrue(schema.isValid("hello world"));
    }

    @Test
    void testContains() {
        schema.contains("hex");

        assertFalse(schema.isValid("hello"));
        assertTrue(schema.isValid("hexlet"));
        assertTrue(schema.isValid("hex"));
    }

    @Test
    void testChainedValidators() {
        schema.required().minLength(5).contains("hex");

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("abc"));
        assertTrue(schema.isValid("hexlet"));

        schema.contains("whatthe");
        assertFalse(schema.isValid("hexlet"));
    }

    @Test
    void testOverwriteValidator() {
        schema.minLength(10);
        assertFalse(schema.isValid("Hexlet"));

        schema.minLength(4);
        assertTrue(schema.isValid("Hexlet"));
    }
}
