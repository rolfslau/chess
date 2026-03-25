package exceptions;

public class DoesNotExistException extends RuntimeException {
    public final int errorCode;
    public DoesNotExistException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
