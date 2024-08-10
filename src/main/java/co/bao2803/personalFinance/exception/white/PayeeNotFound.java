package co.bao2803.personalFinance.exception.white;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

public class PayeeNotFound extends WhiteException {
    private static final String code = "PNF";
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public PayeeNotFound(final UUID badPayeeId) {
        super(code, "No Payee with ID " + badPayeeId, status);
    }

    public PayeeNotFound(final List<UUID> badPayeeIds) {
        super(code, "No Payee with ID " + badPayeeIds, status);
    }
}
