package co.bao2803.personalFinance.dto.payee.update;

import lombok.Value;

import java.util.UUID;

@Value
public class UpdatePayeeRes {
    UUID id;
    String email;
    String phone;
    String name;
}
