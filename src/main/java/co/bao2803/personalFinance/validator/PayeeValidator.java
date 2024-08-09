package co.bao2803.personalFinance.validator;

import co.bao2803.personalFinance.annotation.AtLeastOneNotNull;
import co.bao2803.personalFinance.model.Payee;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PayeeValidator implements ConstraintValidator<AtLeastOneNotNull, Payee> {

    @Override
    public boolean isValid(Payee payee, ConstraintValidatorContext context) {
        return payee.getName() != null || payee.getEmail() != null || payee.getPhone() != null;
    }
}

