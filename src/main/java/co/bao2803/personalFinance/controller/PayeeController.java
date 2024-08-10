package co.bao2803.personalFinance.controller;

import co.bao2803.personalFinance.dto.ResponseDto;
import co.bao2803.personalFinance.dto.payee.create.CreatePayeeReq;
import co.bao2803.personalFinance.dto.payee.create.CreatePayeeRes;
import co.bao2803.personalFinance.dto.payee.read.ReadPayeeReq;
import co.bao2803.personalFinance.dto.payee.read.ReadPayeeRes;
import co.bao2803.personalFinance.dto.payee.update.UpdatePayeeReq;
import co.bao2803.personalFinance.dto.payee.update.UpdatePayeeRes;
import co.bao2803.personalFinance.service.PayeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payee")
@SecurityRequirement(name = "bearerJwt")
public class PayeeController {
    private final PayeeService payeeService;

    @Operation(summary = "Create a new payee. Note: There can be 2 or more payee with same name, email, and phone")
    @PostMapping
    public ResponseDto<CreatePayeeRes> createNewPayee(
            @RequestBody final CreatePayeeReq createPayeeReq
    ) {
        final CreatePayeeRes res = payeeService.createPayee(createPayeeReq);
        return ResponseDto.success(res);
    }

    @Operation(summary = "Find all payees that matches the input email, phone, and name")
    @GetMapping("/search")
    public ResponseDto<List<ReadPayeeRes>> readPayee(
            @ParameterObject final ReadPayeeReq readPayeeReq
    ) {
        final List<ReadPayeeRes> res = payeeService.getAllPayeeWithEmailPhoneAndName(readPayeeReq);
        return ResponseDto.success(res);
    }

    @Operation(summary = "Read one payee using its id")
    @GetMapping("/{payeeId}")
    public ResponseDto<ReadPayeeRes> readPayee(
            @PathVariable final UUID payeeId
    ) {
        final ReadPayeeRes res = payeeService.getOnePayee(payeeId);
        return ResponseDto.success(res);
    }

    @Operation(summary = "Read all payees")
    @GetMapping
    public ResponseDto<Page<ReadPayeeRes>> readPayee(
            @ParameterObject
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        final Page<ReadPayeeRes> res = payeeService.getAllPayees(pageable);
        return ResponseDto.success(res);
    }

    @Operation(summary = "Update a payee")
    @PutMapping("/{payeeId}")
    public ResponseDto<UpdatePayeeRes> updatePayee(
            @PathVariable final UUID payeeId,
            @RequestBody final UpdatePayeeReq updatePayeeReq
    ) {
        updatePayeeReq.setId(payeeId);
        final UpdatePayeeRes res = payeeService.updatePayee(updatePayeeReq);
        return ResponseDto.success(res);
    }
}
