package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
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
    void testStringSchema() {
        StringSchema schema = v.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.required();
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));

        schema.minLength(5);
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hi"));

        schema.contains("ll");
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hlo"));
    }

    @Test
    void testNumberSchema() {
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(5));

        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(5));

        schema.positive();
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(-5));

        schema.range(0, 10);
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(15));
    }

    @Test
    void testMapSchema() {
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        schema.sizeof(2);
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertTrue(schema.isValid(map));

        Map<String, Object> smallMap = new HashMap<>();
        smallMap.put("key1", "value1");
        assertFalse(schema.isValid(smallMap));
    }

    @Test
    void testShape() {
        MapSchema schema = v.map();

        StringSchema nameSchema = v.string().required();
        NumberSchema ageSchema = v.number().positive();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", nameSchema);
        schemas.put("age", ageSchema);

        schema.shape(schemas);

        Map<String, Object> validData = new HashMap<>();
        validData.put("name", "Hexlet");
        validData.put("age", 20);

        assertTrue(schema.isValid(validData));

        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("name", "");
        invalidData.put("age", 20);

        assertFalse(schema.isValid(invalidData));
    }

    @Test
    void testShapeWithNullMap() {
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required());

        schema.shape(schemas);

        assertTrue(schema.isValid(null));
    }
}
