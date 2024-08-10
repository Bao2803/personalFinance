package co.bao2803.personalFinance.dto.payee.create;

import co.bao2803.personalFinance.annotation.AtLeastOneNotNull;
import lombok.Value;

@Value
@AtLeastOneNotNull
public class CreatePayeeReq {
    String email;
    String phone;
    String name;
}
