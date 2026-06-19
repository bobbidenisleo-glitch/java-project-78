package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        validators.put("required", n -> n != null);
        return this;
    }

    public NumberSchema positive() {
        validators.put("positive", n -> n == null || n > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        validators.put("range", n -> n == null || (n >= min && n <= max));
        return this;
    }
}
