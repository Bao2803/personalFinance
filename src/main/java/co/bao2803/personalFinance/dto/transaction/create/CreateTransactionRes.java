package co.bao2803.personalFinance.dto.transaction.create;

import co.bao2803.personalFinance.dto.transaction.Split;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class CreateTransactionRes {
    UUID id;

    Double amount;

    List<Split> splits;
}
