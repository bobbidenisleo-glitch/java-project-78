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

        Map<String, Object> person1 = new HashMap<>();
        person1.put("name", "John");
        person1.put("age", 25);
        assertTrue(schema.isValid(person1));

        Map<String, Object> person2 = new HashMap<>();
        person2.put("name", "");
        person2.put("age", 25);
        assertFalse(schema.isValid(person2));

        Map<String, Object> person3 = new HashMap<>();
        person3.put("name", "Jane");
        person3.put("age", -5);
        assertFalse(schema.isValid(person3));

        Map<String, Object> person4 = new HashMap<>();
        person4.put("name", "Bob");
        assertTrue(schema.isValid(person4));
    }
}

    @Test
    void testPlaceholder() {
        assertTrue(true);
    }
