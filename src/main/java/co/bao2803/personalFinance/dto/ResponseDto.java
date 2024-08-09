package co.bao2803.personalFinance.dto;

import lombok.Data;

public record ResponseDto<T>(T data, ExceptionDto error) {
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(data, null);
    }

    public static ResponseDto<EmptyResponse> success() {
        return new ResponseDto<>(new EmptyResponse(), null);
    }

    public static <T> ResponseDto<T> error(ExceptionDto error) {
        return new ResponseDto<>(null, error);
    }

    @Data
    public static final class EmptyResponse {
    }
}
