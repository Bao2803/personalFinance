package co.bao2803.personalFinance.validator;

import co.bao2803.personalFinance.annotation.AtLeastOneNotNull;
import co.bao2803.personalFinance.dto.payee.create.CreatePayeeReq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CreatePayeeReqValidator implements ConstraintValidator<AtLeastOneNotNull, CreatePayeeReq> {

    @Override
    public boolean isValid(CreatePayeeReq payee, ConstraintValidatorContext context) {
        return payee.getName() != null || payee.getEmail() != null || payee.getPhone() != null;
    }
}

