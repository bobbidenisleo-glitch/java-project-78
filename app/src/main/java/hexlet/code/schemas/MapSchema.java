package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    public MapSchema required() {
        validators.add(m -> m != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        validators.add(m -> m == null || m.size() == size);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        validators.add(m -> {
            if (m == null) return true;
            for (Map.Entry<String, BaseSchema<String>> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<String> schema = entry.getValue();
                Object value = m.get(key);
                if (value == null) continue;
                if (!schema.isValid((String) value)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
