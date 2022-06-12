package fel.cvut.order.exception;

/**
 * Signifies that invalid data have been provided to the application.
 */
public class ValidationException extends CopytoException {

    public ValidationException(String message) {
        super(message);
    }
}
