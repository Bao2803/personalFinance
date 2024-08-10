package co.bao2803.personalFinance.dto.payee.read;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ReadPayeeReq {
    @Email
    private String email = "%";
    private String phone = "%";
    private String name = "%";
}
