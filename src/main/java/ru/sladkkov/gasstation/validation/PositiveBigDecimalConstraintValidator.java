package ru.sladkkov.gasstation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PositiveBigDecimalConstraintValidator implements ConstraintValidator<PositiveConstraint, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        return bigDecimal.compareTo(BigDecimal.ZERO) >= 0;
    }
}
