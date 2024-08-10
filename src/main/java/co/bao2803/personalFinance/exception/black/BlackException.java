package co.bao2803.personalFinance.exception.black;

import co.bao2803.personalFinance.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Exception that is hidden to the user
 */
public abstract class BlackException extends BaseException {
    private static final String codePrefix = "BL";
    private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BlackException(String code, String message) {
        this(code, List.of(message), status, null);
    }

    public BlackException(String code, List<String> messages) {
        this(code, messages, status, null);
    }

    public BlackException(String code, String message, HttpStatus status) {
        this(code, List.of(message), status, null);
    }

    public BlackException(String code, List<String> messages, HttpStatus status) {
        this(code, messages, status, null);
    }

    public BlackException(String code, List<String> messages, HttpStatus status, Throwable e) {
        super(createCode(code), messages, status, e);
    }

    public static String createCode(String code) {
        return codePrefix + "_" + code;
    }
}
