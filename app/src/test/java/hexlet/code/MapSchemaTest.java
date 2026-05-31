package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapSchemaTest {

    private Validator v;
    private MapSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.map();
    }

    @Test
    void testRequired() {
        // Без required() null валиден
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid(Map.of("key", "value")));
    }

    @Test
    void testSizeof() {
        schema.sizeof(2);

        assertTrue(schema.isValid(null)); // null не проверяется на размер
        assertFalse(schema.isValid(Map.of("key1", "value1")));
        assertTrue(schema.isValid(Map.of("key1", "value1", "key2", "value2")));
        assertFalse(schema.isValid(Map.of("key1", "value1", "key2", "value2", "key3", "value3")));
    }

    @Test
    void testChainedValidators() {
        schema.required().sizeof(2);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid(Map.of("key1", "value1")));
        assertTrue(schema.isValid(Map.of("key1", "value1", "key2", "value2")));
    }

    @Test
    void testOverwriteSizeof() {
        schema.sizeof(3);
        assertFalse(schema.isValid(Map.of("key1", "value1", "key2", "value2")));

        schema.sizeof(2);
        assertTrue(schema.isValid(Map.of("key1", "value1", "key2", "value2")));
    }
}
