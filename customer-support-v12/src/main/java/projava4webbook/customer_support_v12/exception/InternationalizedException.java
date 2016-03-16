package projava4webbook.customer_support_v12.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

public class InternationalizedException extends RuntimeException implements MessageSourceResolvable {

    private static final long serialVersionUID = 1L;
    private static final Locale DEFAULT_LOCALE = Locale.US;
    
    private final String errorCode;
    private final String[] codes;
    private final Object[] arguments;
    
    public InternationalizedException(String errorCode, Object... arguments) {
        this(null, errorCode, null, arguments);
    }
    
    public InternationalizedException(Throwable cause, String errorCode, Object... arguments) {
        this(cause, errorCode, null, arguments);
    }
    
    public InternationalizedException(String errorCode, String defaultMessage, Object... arguments) {
        this(null, errorCode, defaultMessage, arguments);
    }

    public InternationalizedException(Throwable cause, String errorCode, String defaultMessage, Object... arguments) {
        super(defaultMessage == null ? errorCode : defaultMessage, cause);
        this.errorCode = errorCode;
        this.codes = new String[] {errorCode};
        this.arguments = arguments;
    }
    
    @Override
    public String getLocalizedMessage() {
        return this.errorCode;
    }
    
    public String getLocalizedMessage(MessageSource messageSource) {
        return getLocalizedMessage(messageSource, this.getLocale());
    }
    
    protected Locale getLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale == null ? InternationalizedException.DEFAULT_LOCALE : locale;
    }

    public String getLocalizedMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage(this, locale);
    }

    /* MessageSourceResolvable interface methods: */
    
    @Override
    public String[] getCodes() {
        return this.codes;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public String getDefaultMessage() {
        return this.getMessage();
    }

}
