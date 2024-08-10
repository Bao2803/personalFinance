package co.bao2803.personalFinance.exception.white;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class TransactionNotFound extends WhiteException {
    private static final String code = "TNF";
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public TransactionNotFound(final UUID badTransactionId) {
        super(code, "No transaction with Id " + badTransactionId, status);
    }
}
