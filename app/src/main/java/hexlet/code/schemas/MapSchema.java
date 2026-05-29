package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    private Predicate<Map<String, Object>> sizeValidator = null;

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
}
