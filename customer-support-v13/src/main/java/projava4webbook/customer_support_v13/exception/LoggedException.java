package projava4webbook.customer_support_v13.exception;

public class LoggedException extends InternationalizedException {

    private static final long serialVersionUID = 1L;

    public LoggedException(Throwable cause, String errorCode, Object... arguments) {
        this(cause, errorCode, null, arguments);
    }
    
    public LoggedException(Throwable cause, String errorCode, String defaultMessage, Object... arguments) {
        super(cause, errorCode, defaultMessage, arguments);
    }

}
