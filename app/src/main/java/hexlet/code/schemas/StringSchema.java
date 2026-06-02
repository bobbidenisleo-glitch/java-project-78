package hexlet.code.schemas;

import java.util.function.Predicate;

public class StringSchema extends BaseSchema<String> {

    private boolean required = false;

    public StringSchema required() {
        required = true;
        return this;
    }

    public StringSchema minLength(int length) {
        validators.add(s -> {
            if (s == null) return !required;
            return s.length() >= length;
        });
        return this;
    }

    public StringSchema contains(String substring) {
        validators.add(s -> {
            if (s == null) return !required;
            return s.contains(substring);
        });
        return this;
    }

    @Override
    public boolean isValid(String value) {
        if (required && (value == null || value.isEmpty())) {
            return false;
        }
        return super.isValid(value);
    }
}
