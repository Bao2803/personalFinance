package co.bao2803.personalFinance.dto.payee.create;

import lombok.Value;

import java.util.UUID;

@Value
public class CreatePayeeRes {
    UUID id;
    String email;
    String phone;
    String name;
}
