package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map> {

    public MapSchema required() {
        validators.put("required", map -> map != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        validators.put("sizeof", map -> map == null || map.size() == size);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        validators.put("shape", map -> {
            if (map == null) {
                return true;
            }

            for (Map.Entry<String, ? extends BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();

                BaseSchema<Object> schema =
                        (BaseSchema<Object>) entry.getValue();

                Object value = map.get(key);

                if (!schema.isValid(value)) {
                    return false;
                }
            }

            return true;
        });

        return this;
    }
}
