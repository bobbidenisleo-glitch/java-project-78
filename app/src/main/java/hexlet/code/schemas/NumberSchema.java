package hexlet.code.schemas;

import java.util.function.Predicate;

public class NumberSchema extends BaseSchema<Integer> {
    private Predicate<Integer> rangeValidator = null;

    public NumberSchema required() {
        validators.add(n -> n != null);
        return this;
    }

    public NumberSchema positive() {
        validators.add(n -> n == null || n > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        // Удаляем предыдущий range-валидатор, если он был
        if (rangeValidator != null) {
            validators.remove(rangeValidator);
        }
        rangeValidator = n -> n == null || (n >= min && n <= max);
        validators.add(rangeValidator);
        return this;
    }
}
