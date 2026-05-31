package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<String, Object>> {
    private Predicate<Map<String, Object>> sizeValidator = null;

    public MapSchema required() {
        validators.add(m -> m != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        // Удаляем предыдущий size-валидатор
        if (sizeValidator != null) {
            validators.remove(sizeValidator);
        }
        sizeValidator = m -> m == null || m.size() == size;
        validators.add(sizeValidator);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        validators.add(m -> {
            if (m == null) return true;
            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<?> schema = entry.getValue();
                Object value = m.get(key);
                
                if (value == null && schema.isValid(null)) {
                    continue;
                }
                
                if (!((BaseSchema<Object>) schema).isValid(value)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
