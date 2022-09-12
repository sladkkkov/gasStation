package ru.sladkkov.gasstation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveConstraintValidator implements ConstraintValidator<PositiveConstraint, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value > 0;
    }

}
