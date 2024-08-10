package co.bao2803.personalFinance.mapper;

import co.bao2803.personalFinance.dto.transaction.Split;
import co.bao2803.personalFinance.dto.transaction.create.CreateTransactionRes;
import co.bao2803.personalFinance.model.Transaction;
import co.bao2803.personalFinance.model.TransactionPayee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "splits", source = "transactionPayees")
    CreateTransactionRes transactionToCreateTransactionRes(
            Transaction transaction,
            List<TransactionPayee> transactionPayees
    );

    @Mapping(target = "payeeName", source = "payee.name")
    Split transactionPayeeToSplit(TransactionPayee transactionPayee);
}
