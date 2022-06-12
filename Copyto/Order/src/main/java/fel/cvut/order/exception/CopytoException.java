package fel.cvut.order.exception;

/**
 * Base for all application-specific exceptions.
 */
public class CopytoException extends RuntimeException {

    public CopytoException() {
    }

    public CopytoException(String message) {
        super(message);
    }

    public CopytoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopytoException(Throwable cause) {
        super(cause);
    }
}
