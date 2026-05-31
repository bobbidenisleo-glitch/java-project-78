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
        assertTrue(schema.isValid(null));
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    void testSizeof() {
        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        schema.sizeof(2);
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(data));
        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }

    @Test
    void testRequiredAndSizeof() {
        schema.required().sizeof(1);
        assertFalse(schema.isValid(null));
        Map<String, Object> data = new HashMap<>();
        assertFalse(schema.isValid(data));
        data.put("key", "value");
        assertTrue(schema.isValid(data));
    }
}
