package ru.sladkkov.gasstation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PositiveIntegerConstraintValidator.class, PositiveBigDecimalConstraintValidator.class})
public @interface PositiveConstraint {
    String message() default "Value must be positive";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
