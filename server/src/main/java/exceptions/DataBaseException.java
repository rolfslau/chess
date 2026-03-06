package exceptions;

public class DataBaseException extends RuntimeException {
    public final int errorCode;
    public DataBaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
