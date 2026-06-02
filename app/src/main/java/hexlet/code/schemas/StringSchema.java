package hexlet.code.schemas;

import java.util.function.Predicate;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        validators.add(s -> s != null && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        validators.add(s -> s != null && s.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        validators.add(s -> s != null && s.contains(substring));
        return this;
    }
}
