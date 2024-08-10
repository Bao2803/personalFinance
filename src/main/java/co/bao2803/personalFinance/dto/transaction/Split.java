package co.bao2803.personalFinance.dto.transaction;

import lombok.Value;

import java.util.UUID;

@Value
public class Split {
    UUID payeeId;
    String payeeName;
    Double amount;
}
