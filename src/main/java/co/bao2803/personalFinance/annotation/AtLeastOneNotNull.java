package co.bao2803.personalFinance.annotation;

import co.bao2803.personalFinance.validator.PayeeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PayeeValidator.class)
public @interface AtLeastOneNotNull {
    String message() default "At least one of name, email, or phone must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
