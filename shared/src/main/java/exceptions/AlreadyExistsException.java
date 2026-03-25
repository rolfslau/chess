package exceptions;

public class AlreadyExistsException extends RuntimeException{

    public final int errorCode;
    public AlreadyExistsException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
