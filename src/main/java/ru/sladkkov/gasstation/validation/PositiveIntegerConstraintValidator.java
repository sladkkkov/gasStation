package ru.sladkkov.gasstation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveIntegerConstraintValidator implements ConstraintValidator<PositiveConstraint, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value >= 0;
    }

}
