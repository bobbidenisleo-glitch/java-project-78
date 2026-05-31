package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        validators.add(n -> n != null);
        return this;
    }

    public NumberSchema positive() {
        validators.add(n -> n == null || n > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        validators.add(n -> n == null || (n >= min && n <= max));
        return this;
    }
}
