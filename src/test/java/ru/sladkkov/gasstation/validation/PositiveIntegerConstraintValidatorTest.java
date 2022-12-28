package ru.sladkkov.gasstation.validation;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sladkkov.gasstation.model.User;
import ru.sladkkov.gasstation.topology.freemapelement.Car;
import ru.sladkkov.gasstation.topology.freemapelement.Fuel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositiveIntegerConstraintValidatorTest {
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void testCarValidatorsWithFalseParameters() {
        final Car car = new Car("12345678910111213", "M6", BigDecimal.valueOf(-100), BigDecimal.valueOf(-1),
                null);

        Set<ConstraintViolation<Car>> validates = validator.validate(car);
        assertTrue(validates.size() > 0);
        validates.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
    }

    @Test
    void testCarValidationWithTrueParameters() {
        final Fuel fuel = Mockito.mock(Fuel.class);
        final Car car = new Car("BMW", "M", BigDecimal.valueOf(25), BigDecimal.valueOf(10),
                fuel);

        Set<ConstraintViolation<Car>> validates = validator.validate(car);
        assertEquals(0, validates.size());

    }

    @Test
    void testUserValidatorsWithFalseParameters() {
        final User user = Mockito.mock(User.class);

        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertTrue(validates.size() > 0);
        validates.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
    }


    @Test
    void testFuelValidatorsWithFalseParameters() {
        final Fuel fuel = new Fuel("95", BigDecimal.valueOf(-5));
        Set<ConstraintViolation<Fuel>> validates = validator.validate(fuel);
        assertTrue(validates.size() > 0);
        validates.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
    }


}
