package co.bao2803.personalFinance.controller;

import co.bao2803.personalFinance.dto.ResponseDto;
import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionReq;
import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionRes;
import co.bao2803.personalFinance.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerJwt")
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseDto<CreateTransactionRes> createTransaction(
            @RequestBody final CreateTransactionReq transactionReq
    ) {
        final CreateTransactionRes createTransactionRes = transactionService.createTransaction(transactionReq);
        return ResponseDto.success(createTransactionRes);
    }
}
