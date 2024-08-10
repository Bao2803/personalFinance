package co.bao2803.personalFinance.service;

import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionReq;
import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionRes;
import co.bao2803.personalFinance.dto.transaction.read.ReadTransactionRes;
import co.bao2803.personalFinance.mapper.TransactionMapper;
import co.bao2803.personalFinance.model.Payee;
import co.bao2803.personalFinance.model.Transaction;
import co.bao2803.personalFinance.model.TransactionPayee;
import co.bao2803.personalFinance.repository.PayeeRepository;
import co.bao2803.personalFinance.repository.TransactionPayeeRepository;
import co.bao2803.personalFinance.repository.TransactionRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final PayeeRepository payeeRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionPayeeRepository transactionPayeeRepository;

    @Transactional
    public CreateTransactionRes createTransaction(@Nonnull final CreateTransactionReq createPayeeReq) {
        final List<Payee> payees = payeeRepository.findAllById(createPayeeReq.getPayees());
        if (payees.size() != createPayeeReq.getPayees().size()) {
            final Set<UUID> badIds = new HashSet<>(createPayeeReq.getPayees());
            for (Payee payee : payees) {
                badIds.remove(payee.getId());
            }
            throw new RuntimeException("Non exist id: " + badIds);
        }

        // Create a transaction
        final Transaction transaction = Transaction.builder()
                .amount(createPayeeReq.getAmount())
                .build();
        final Transaction savedTransaction = transactionRepository.save(transaction);

        // Create set of transaction payees
        final double splitAmount = createPayeeReq.getAmount() / payees.size();
        List<TransactionPayee> transactionPayees = payees.stream()
                .map(payee -> TransactionPayee.builder()
                        .amount(splitAmount)
                        .payeeId(payee.getId())
                        .payee(payee)
                        .transactionId(savedTransaction.getId())
                        .build()
                )
                .toList();
        transactionPayees = transactionPayeeRepository.saveAll(transactionPayees);

        // TODO: add 3rd APIs to send money?

        return transactionMapper.transactionToCreateTransactionRes(savedTransaction, transactionPayees);
    }

    @Transactional(readOnly = true)
    public ReadTransactionRes readTransaction(@Nonnull final UUID transactionId) {
        final Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow();
        return transactionMapper.transactionToReadTransaction(transaction);
    }

    @Transactional(readOnly = true)
    public Page<ReadTransactionRes> listTransaction(@Nonnull final Pageable pageable) {
        return transactionRepository.findAll(pageable)
                .map(transactionMapper::transactionToReadTransaction);
    }
}
