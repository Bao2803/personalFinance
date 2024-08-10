package co.bao2803.personalFinance.dto.transaction.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
public class CreateTransactionReq {
    @NotNull(message = "transaction amount cannot be NULL")
    @Min(value = 0, message = "transaction amount must be 0 or greater")
    Double amount;

    @NotEmpty(message = "payees cannot be empty")
    Set<UUID> payees;
}
