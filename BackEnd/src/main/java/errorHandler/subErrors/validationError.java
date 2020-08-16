package errorHandler.subErrors;

public class validationError extends AbstractSubError {
    private String objectType;
    private String failedField;
    private Object rejectedVal;
    private String message;

    public validationError(String objectType, String message) {
        this.objectType = objectType;
        this.message = message;
    }
}
