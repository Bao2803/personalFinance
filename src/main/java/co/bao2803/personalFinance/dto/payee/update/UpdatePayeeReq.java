package co.bao2803.personalFinance.dto.payee.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.UUID;

@Value
public class UpdatePayeeReq {
    String email;
    String phone;
    String name;

    @Setter
    @NonFinal
    @JsonIgnore
    UUID id;
}
