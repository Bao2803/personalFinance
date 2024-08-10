package co.bao2803.personalFinance.exception.white;

import co.bao2803.personalFinance.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Exception that will display the message to the user
 */
public abstract class WhiteException extends BaseException {
    private static final String codePrefix = "WH";

    public WhiteException(String code, String message, HttpStatus status) {
        this(code, List.of(message), status, null);
    }

    public WhiteException(String code, List<String> messages, HttpStatus status) {
        this(code, messages, status, null);
    }

    public WhiteException(String code, List<String> messages, HttpStatus status, Throwable e) {
        super(createCode(code), messages, status, e);
    }

    public static String createCode(String code) {
        return codePrefix + "_" + code;
    }
}
