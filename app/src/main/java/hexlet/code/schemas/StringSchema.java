package hexlet.code.schemas;

import java.util.function.Predicate;

public class StringSchema extends BaseSchema<String> {

    private Predicate<String> minLengthValidator = null;
    private Predicate<String> containsValidator = null;

    public StringSchema required() {
        validators.add(s -> s != null && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        // Удаляем предыдущий валидатор minLength, если был
        if (minLengthValidator != null) {
            validators.remove(minLengthValidator);
        }
        minLengthValidator = s -> s != null && s.length() >= length;
        validators.add(minLengthValidator);
        return this;
    }

    public StringSchema contains(String substring) {
        // Удаляем предыдущий валидатор contains, если был
        if (containsValidator != null) {
            validators.remove(containsValidator);
        }
        containsValidator = s -> s != null && s.contains(substring);
        validators.add(containsValidator);
        return this;
    }
}
