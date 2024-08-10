package co.bao2803.personalFinance.controller;

import co.bao2803.personalFinance.dto.ResponseDto;
import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionReq;
import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionRes;
import co.bao2803.personalFinance.dto.transaction.read.ReadTransactionRes;
import co.bao2803.personalFinance.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerJwt")
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Create a new transaction")
    @PostMapping
    public ResponseDto<CreateTransactionRes> createTransaction(
            @RequestBody final CreateTransactionReq transactionReq
    ) {
        final CreateTransactionRes createTransactionRes = transactionService.createTransaction(transactionReq);
        return ResponseDto.success(createTransactionRes);
    }

    @Operation(summary = "Read a transaction")
    @GetMapping("/{transactionId}")
    public ResponseDto<ReadTransactionRes> readTransaction(
            @PathVariable final UUID transactionId
    ) {
        final ReadTransactionRes readTransactionRes = transactionService.readTransaction(transactionId);
        return ResponseDto.success(readTransactionRes);
    }

    @Operation(summary = "Get a page of transaction")
    @GetMapping
    public ResponseDto<Page<ReadTransactionRes>> listTransaction(
            @ParameterObject
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        final Page<ReadTransactionRes> resPage = transactionService.listTransaction(pageable);
        return ResponseDto.success(resPage);
    }
}
