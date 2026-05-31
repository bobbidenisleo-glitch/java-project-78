package hexlet.code;

import hexlet.code.Validator;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator v;

    @BeforeEach
    void setUp() {
        v = new Validator();
    }

    @Test
    void testMapSchemaShape() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());

        MapSchema schema = v.map().shape(schemas);

        Map<String, Object> actual1 = new HashMap<>();
        actual1.put("name", "John");
        actual1.put("age", 25);
        assertTrue(schema.isValid(actual1));

        Map<String, Object> actual2 = new HashMap<>();
        actual2.put("name", "");
        actual2.put("age", 25);
        assertFalse(schema.isValid(actual2));

        Map<String, Object> actual3 = new HashMap<>();
        actual3.put("name", "Jane");
        actual3.put("age", -5);
        assertFalse(schema.isValid(actual3));

        Map<String, Object> actual4 = new HashMap<>();
        actual4.put("name", "Bob");
        assertTrue(schema.isValid(actual4));
    }
}
