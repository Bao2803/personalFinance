package co.bao2803.personalFinance.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TransactionPayeeKey {
    private UUID payeeId;
    private UUID transactionId;
}
