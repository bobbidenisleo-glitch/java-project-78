package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StringSchema {

    private List<Predicate<String>> validators = new ArrayList<>();
    private Predicate<String> requiredValidator = null;
    private Predicate<String> minLengthValidator = null;
    private Predicate<String> containsValidator = null;

    public StringSchema required() {
        requiredValidator = s -> s != null && !s.isEmpty();
        rebuildValidators();
        return this;
    }

    public StringSchema minLength(int length) {
        minLengthValidator = s -> s != null && s.length() >= length;
        rebuildValidators();
        return this;
    }

    public StringSchema contains(String substring) {
        containsValidator = s -> s != null && s.contains(substring);
        rebuildValidators();
        return this;
    }

    private void rebuildValidators() {
        validators.clear();
        if (requiredValidator != null) {
            validators.add(requiredValidator);
        }
        if (minLengthValidator != null) {
            validators.add(minLengthValidator);
        }
        if (containsValidator != null) {
            validators.add(containsValidator);
        }
    }

    public boolean isValid(String value) {
        if (validators.isEmpty()) {
            return true;
        }
        return validators.stream().allMatch(v -> v.test(value));
    }
}
