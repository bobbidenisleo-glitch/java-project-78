package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    private Predicate<Map<String, Object>> sizeValidator = null;
    private Map<String, BaseSchema<?>> shapeSchemas = null;

    @Override
    public MapSchema required() {
        requiredValidator = value -> value != null;
        rebuildValidators();
        return this;
    }

    public MapSchema sizeof(int size) {
        sizeValidator = value -> value == null || value.size() == size;
        rebuildValidators();
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        this.shapeSchemas = schemas;
        return this;
    }

    @Override
    protected void rebuildValidators() {
        validators.clear();
        if (requiredValidator != null) {
            validators.add(requiredValidator);
        }
        if (sizeValidator != null) {
            validators.add(sizeValidator);
        }
    }

    @Override
    public boolean isValid(Map<String, Object> value) {
        // Проверяем required и size
        if (!super.isValid(value)) {
            return false;
        }

        // Если нет shape-схем или значение null — всё ок
        if (shapeSchemas == null || value == null) {
            return true;
        }

        // Проверяем каждое поле из shapeSchemas
        for (Map.Entry<String, BaseSchema<?>> entry : shapeSchemas.entrySet()) {
            String fieldName = entry.getKey();
            BaseSchema<?> fieldSchema = entry.getValue();
            
            // Если поля нет в мапе — пропускаем (оно не обязательно)
            if (!value.containsKey(fieldName)) {
                continue;
            }
            
            Object fieldValue = value.get(fieldName);
            
            @SuppressWarnings("unchecked")
            BaseSchema<Object> typedSchema = (BaseSchema<Object>) fieldSchema;
            if (!typedSchema.isValid(fieldValue)) {
                return false;
            }
        }

        return true;
    }
}
