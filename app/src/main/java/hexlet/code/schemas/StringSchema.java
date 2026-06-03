package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        validators.add(s -> s != null && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        validators.add(s -> s == null || s.isEmpty() || s.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        validators.add(s -> s == null || s.isEmpty() || s.contains(substring));
        return this;
    }
}
