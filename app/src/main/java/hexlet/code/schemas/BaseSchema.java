package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    protected List<Predicate<T>> validators = new ArrayList<>();
    protected Predicate<T> requiredValidator = null;
    protected Predicate<T> positiveValidator = null;

    public BaseSchema<T> required() {
        requiredValidator = value -> value != null;
        rebuildValidators();
        return this;
    }

    protected void rebuildValidators() {
        validators.clear();
        if (requiredValidator != null) {
            validators.add(requiredValidator);
        }
        if (positiveValidator != null) {
            validators.add(positiveValidator);
        }
    }

    public boolean isValid(T value) {
        if (validators.isEmpty()) {
            return true;
        }
        return validators.stream().allMatch(v -> v.test(value));
    }
}
