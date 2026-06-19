package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        validators.put(
            "required",
            s -> s != null && !s.isEmpty()
        );
        return this;
    }

    public StringSchema minLength(int length) {
        validators.put(
            "minLength",
            s -> s != null && s.length() >= length
        );
        return this;
    }

    public StringSchema contains(String substring) {
        validators.put(
            "contains",
            s -> s != null && s.contains(substring)
        );
        return this;
    }
}
