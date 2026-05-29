package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberSchemaTest {

    private Validator v;
    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.number();
    }

    @Test
    void testRequired() {
        // Без required() null валиден
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(5));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(-10));
    }

    @Test
    void testPositive() {
        schema.positive();

        assertTrue(schema.isValid(null)); // positive не запрещает null
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid(0));
    }

    @Test
    void testRange() {
        schema.range(5, 10);

        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(7));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));
        assertTrue(schema.isValid(null)); // range не запрещает null
    }

    @Test
    void testChainedValidators() {
        schema.required().positive().range(5, 10);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(4));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(7));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(11));
    }

    @Test
    void testOverwriteRange() {
        schema.range(10, 20);
        assertFalse(schema.isValid(5));

        schema.range(1, 5);
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(10));
    }
}
