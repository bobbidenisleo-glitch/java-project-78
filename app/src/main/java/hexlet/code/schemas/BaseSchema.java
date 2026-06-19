package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    protected Map<String, Predicate<T>> validators = new HashMap<>();

    public boolean isValid(T value) {
        for (Predicate<T> validator : validators.values()) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }
}
