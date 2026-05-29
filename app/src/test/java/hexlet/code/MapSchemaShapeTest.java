package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapSchemaShapeTest {

    private Validator v;
    private MapSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.map();
    }

    @Test
    void testShapeWithRequiredString() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("firstName", null);
        human4.put("lastName", "Johnson");
        assertFalse(schema.isValid(human4));
    }

    @Test
    void testShapeWithNumberSchema() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("age", v.number().required().positive().range(18, 99));

        schema.shape(schemas);

        Map<String, Object> person1 = new HashMap<>();
        person1.put("age", 25);
        assertTrue(schema.isValid(person1));

        Map<String, Object> person2 = new HashMap<>();
        person2.put("age", 17);
        assertFalse(schema.isValid(person2));

        Map<String, Object> person3 = new HashMap<>();
        person3.put("age", 100);
        assertFalse(schema.isValid(person3));

        Map<String, Object> person4 = new HashMap<>();
        person4.put("age", null);
        assertFalse(schema.isValid(person4));
    }

    @Test
    void testShapeWithMultipleFields() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required().minLength(3));
        schemas.put("age", v.number().required().positive().range(1, 120));

        schema.shape(schemas);

        Map<String, Object> person = new HashMap<>();
        person.put("name", "Bob");
        person.put("age", 30);
        assertTrue(schema.isValid(person));

        Map<String, Object> person2 = new HashMap<>();
        person2.put("name", "Jo");
        person2.put("age", 30);
        assertFalse(schema.isValid(person2));

        Map<String, Object> person3 = new HashMap<>();
        person3.put("name", "Bob");
        person3.put("age", 0);
        assertFalse(schema.isValid(person3));
    }

    @Test
    void testShapeWithoutRequiredField() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("optional", v.string().minLength(3));

        schema.shape(schemas);

        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("optional", "abc");
        assertTrue(schema.isValid(obj1));

        Map<String, Object> obj2 = new HashMap<>();
        obj2.put("optional", "ab");
        assertFalse(schema.isValid(obj2));

        Map<String, Object> obj3 = new HashMap<>();
        assertTrue(schema.isValid(obj3));
    }
}
