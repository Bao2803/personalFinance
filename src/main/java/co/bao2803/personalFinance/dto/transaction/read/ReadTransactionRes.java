package co.bao2803.personalFinance.dto.transaction.read;

import co.bao2803.personalFinance.dto.transaction.Split;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ReadTransactionRes {
    private UUID transactionId;
    private Double total;
    private List<Split> receivers;
    private Instant createdAt;
}
