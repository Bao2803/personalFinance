package co.bao2803.personalFinance.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
public class ExceptionDto {
    String code;
    List<String> message;

    public ExceptionDto(String code, String messages) {
        this.code = code;
        this.message = new ArrayList<>();
        this.message.add(messages);
    }
}
