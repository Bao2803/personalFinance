package co.bao2803.personalFinance.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public abstract class BaseException extends RuntimeException {
    public static final String defaultCode = "BL_UNK";
    public static final HttpStatus defaultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final String defaultMsg = "Something went wrong, please contact us if the problem persist";

    private final String code;
    private final HttpStatus status;
    private final List<String> messages;

    public BaseException(String code, List<String> messages, HttpStatus status, Throwable e) {
        super(messages.toString(), e);
        this.code = code;
        this.status = status;
        this.messages = messages;
    }
}
