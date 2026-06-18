package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        validators.removeIf(p -> p.toString().contains("required"));
        validators.add(s -> s != null && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        validators.removeIf(p -> p.toString().contains("minLength"));
        validators.add(s -> s != null && s.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        validators.removeIf(p -> p.toString().contains("contains"));
        validators.add(s -> s != null && s.contains(substring));
        return this;
    }
}
