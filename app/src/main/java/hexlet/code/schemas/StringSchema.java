package hexlet.code.schemas;

import java.util.function.Predicate;

public class StringSchema extends BaseSchema<String> {

    private Predicate<String> minLengthValidator = null;
    private Predicate<String> containsValidator = null;

    @Override
    public StringSchema required() {
        requiredValidator = value -> value != null && !value.isEmpty();
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

    @Override
    protected void rebuildValidators() {
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
}
