package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    private boolean requiredFlag = false;
    private Integer minLengthValue = null;
    private String containsValue = null;

    public StringSchema required() {
        requiredFlag = true;
        return this;
    }

    public StringSchema minLength(int length) {
        minLengthValue = length;
        return this;
    }

    public StringSchema contains(String substring) {
        containsValue = substring;
        return this;
    }

    @Override
    public boolean isValid(String value) {
        if (value == null) {
            return !requiredFlag;
        }
        
        if (requiredFlag && value.isEmpty()) {
            return false;
        }
        
        if (minLengthValue != null && value.length() < minLengthValue) {
            return false;
        }
        
        if (containsValue != null && !value.contains(containsValue)) {
            return false;
        }
        
        return true;
    }
}
