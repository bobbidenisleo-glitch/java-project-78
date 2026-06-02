package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map> {

    public MapSchema required() {
        validators.add(m -> m != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        validators.add(m -> m == null || m.size() == size);
        return this;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {

        validators.add(m -> {
            if (m == null) return true;

            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema schema = entry.getValue(); // raw type

                Object value = m.get(key);

                if (!schema.isValid(value)) {
                    return false;
                }
            }

            return true;
        });

        return this;
    }
}
