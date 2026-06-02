package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    protected List<Predicate<T>> validators = new ArrayList<>();

    public boolean isValid(T value) {
        for (Predicate<T> v : validators) {
            if (!v.test(value)) {
                return false;
            }
        }
        return true;
    }
}
