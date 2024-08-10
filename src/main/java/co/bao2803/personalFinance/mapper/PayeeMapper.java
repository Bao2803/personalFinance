package co.bao2803.personalFinance.mapper;

import co.bao2803.personalFinance.dto.payee.create.CreatePayeeReq;
import co.bao2803.personalFinance.dto.payee.read.ReadPayeeRes;
import co.bao2803.personalFinance.dto.payee.update.UpdatePayeeReq;
import co.bao2803.personalFinance.model.Payee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PayeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionPayees", ignore = true)
    Payee createPayeeReqToPayee(final CreatePayeeReq createPayeeReq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePayeeNotNullFields(
            final UpdatePayeeReq updatePayeeReq,
            @MappingTarget final Payee payee
    );

    ReadPayeeRes payeeToReadPayee(final Payee payee);
}
