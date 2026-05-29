package hexlet.code.schemas;

import java.util.function.Predicate;

public class NumberSchema extends BaseSchema<Integer> {

    private Predicate<Integer> rangeMinValidator = null;
    private Predicate<Integer> rangeMaxValidator = null;

    @Override
    public NumberSchema required() {
        super.required();
        return this;
    }

    public NumberSchema positive() {
        positiveValidator = value -> value == null || value > 0;
        rebuildValidators();
        return this;
    }

    public NumberSchema range(int min, int max) {
        rangeMinValidator = value -> value == null || value >= min;
        rangeMaxValidator = value -> value == null || value <= max;
        rebuildValidators();
        return this;
    }

    @Override
    protected void rebuildValidators() {
        super.rebuildValidators();
        if (rangeMinValidator != null) {
            validators.add(rangeMinValidator);
        }
        if (rangeMaxValidator != null) {
            validators.add(rangeMaxValidator);
        }
    }
}
