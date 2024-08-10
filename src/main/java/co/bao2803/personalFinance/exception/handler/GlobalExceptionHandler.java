package co.bao2803.personalFinance.exception.handler;


import co.bao2803.personalFinance.dto.ExceptionDto;
import co.bao2803.personalFinance.dto.ResponseDto;
import co.bao2803.personalFinance.exception.black.BlackException;
import co.bao2803.personalFinance.exception.white.WhiteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class, BlackException.class})
    protected ResponseEntity<ResponseDto<ExceptionDto>> unexpectExceptionHandler(
            final Exception e
    ) {
        log.error(String.valueOf(e));
        e.printStackTrace(System.err);

        // Construct exception
        final HttpStatus status;
        final ExceptionDto exceptionDTO;
        if (e instanceof BlackException blackException) {
            status = blackException.getStatus();
            exceptionDTO = new ExceptionDto(blackException.getCode(), blackException.getMessages());
        } else {
            status = BlackException.defaultStatus;
            exceptionDTO = new ExceptionDto(BlackException.defaultCode, BlackException.defaultMsg);
        }

        // Construct response
        final ResponseDto<ExceptionDto> body = ResponseDto.error(
                exceptionDTO
        );
        return ResponseEntity
                .status(status)
                .body(body);
    }

    @ExceptionHandler
    protected ResponseEntity<ResponseDto<ExceptionDto>> accessDeniedHandler(
            AccessDeniedException e
    ) {
        log.info(String.valueOf(e));

        final ResponseDto<ExceptionDto> body = ResponseDto.error(
                new ExceptionDto("WH_ADE", "Access Denied")
        );
        body.error().getMessage().add(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    // Handle validation error
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        final List<String> errorList = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        final ResponseDto<ExceptionDto> body = ResponseDto.error(new ExceptionDto("WH_BRE", errorList));
        return this.handleExceptionInternal(e, body, headers, status, request);
    }

    @ExceptionHandler
    protected ResponseEntity<ResponseDto<ExceptionDto>> expectExceptionHandler(
            final WhiteException e
    ) {
        log.info(String.valueOf(e));

        final ResponseDto<ExceptionDto> body = ResponseDto.error(new ExceptionDto(e.getCode(), e.getMessages()));
        return ResponseEntity
                .status(e.getStatus())
                .body(body);
    }
}
