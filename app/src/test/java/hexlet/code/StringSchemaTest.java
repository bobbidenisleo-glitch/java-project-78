package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringSchemaTest {

    @Test
    void testRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hello"));
    }

    @Test
    void testMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(5);
        assertFalse(schema.isValid("abc"));
        assertTrue(schema.isValid("abcde"));
        assertTrue(schema.isValid("abcdef"));
    }

    @Test
    void testContains() {
        Validator v = new Validator();
        StringSchema schema = v.string().contains("abc");
        assertFalse(schema.isValid("hello"));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid("123abc456"));
    }
}
