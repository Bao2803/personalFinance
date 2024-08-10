package co.bao2803.personalFinance.dto.payee.read;

import lombok.Value;

import java.util.UUID;

@Value
public class ReadPayeeRes {
    UUID id;
    String email;
    String phone;
    String name;
}
