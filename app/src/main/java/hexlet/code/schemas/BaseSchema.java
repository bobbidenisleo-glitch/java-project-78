package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected List<Predicate<T>> validators = new ArrayList<>();

    public boolean isValid(T value) {
        return validators.stream().allMatch(v -> v.test(value));
    }
}
