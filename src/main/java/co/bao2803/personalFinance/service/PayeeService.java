package co.bao2803.personalFinance.service;

import co.bao2803.personalFinance.dto.payee.create.CreatePayeeReq;
import co.bao2803.personalFinance.dto.payee.create.CreatePayeeRes;
import co.bao2803.personalFinance.dto.payee.read.ReadPayeeRes;
import co.bao2803.personalFinance.dto.payee.update.UpdatePayeeReq;
import co.bao2803.personalFinance.dto.payee.update.UpdatePayeeRes;
import co.bao2803.personalFinance.exception.white.PayeeNotFound;
import co.bao2803.personalFinance.mapper.PayeeMapper;
import co.bao2803.personalFinance.model.Payee;
import co.bao2803.personalFinance.repository.PayeeRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayeeService {
    private final PayeeMapper payeeMapper;
    private final PayeeRepository payeeRepository;

    private Payee getPayeeFromDb(@Nonnull final UUID payeeId) {
        return payeeRepository.findById(payeeId)
                .orElseThrow(() -> new PayeeNotFound(payeeId));
    }

    @Transactional(readOnly = true)
    public List<ReadPayeeRes> searchAllByKeyword(@Nonnull final String keyword) {
        return payeeRepository.searchAllByKeyword(keyword)
                .map(payeeMapper::payeeToReadPayee)
                .toList();
    }

    public Page<ReadPayeeRes> getAllPayees(@Nonnull Pageable pageable) {
        return payeeRepository.findAll(pageable)
                .map(payeeMapper::payeeToReadPayee);
    }

    public ReadPayeeRes getOnePayee(@Nonnull final UUID payeeId) {
        final Payee payee = getPayeeFromDb(payeeId);
        return payeeMapper.payeeToReadPayee(payee);
    }

    public CreatePayeeRes createPayee(@Nonnull final CreatePayeeReq createPayeeReq) {
        Payee newPayee = payeeMapper.createPayeeReqToPayee(createPayeeReq);
        newPayee = payeeRepository.save(newPayee);

        return new CreatePayeeRes(newPayee.getId(), newPayee.getEmail(), newPayee.getPhone(), newPayee.getName());
    }

    public UpdatePayeeRes updatePayee(@Nonnull final UpdatePayeeReq updatePayeeReq) {
        Payee payee = getPayeeFromDb(updatePayeeReq.getId());
        payeeMapper.updatePayeeNotNullFields(updatePayeeReq, payee);
        payee = payeeRepository.save(payee);

        return new UpdatePayeeRes(payee.getId(), payee.getEmail(), payee.getPhone(), payee.getName());
    }
}
