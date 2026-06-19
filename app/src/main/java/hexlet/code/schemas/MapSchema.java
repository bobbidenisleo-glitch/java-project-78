package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    public MapSchema required() {
        validators.put("required", m -> m != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        validators.put("sizeof", m -> m == null || m.size() == size);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        validators.put("shape", m -> {
            if (m == null) {
                return true;
            }
            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<Object> schema = (BaseSchema<Object>) entry.getValue();
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
// updated
