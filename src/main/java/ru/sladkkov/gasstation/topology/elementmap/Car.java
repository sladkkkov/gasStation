package ru.sladkkov.gasstation.topology.elementmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sladkkov.gasstation.topology.elementmap.impl.Payable;
import ru.sladkkov.gasstation.validation.PositiveConstraint;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Car implements Payable {

    @NotEmpty(message = "The brand cannot be empty")
    @Pattern(regexp = "[A-z]+", message = "Car brand must be only of letters")
    @Size(max = 12, message = "Quantity letters of car brand must be less then 12")
    private String brand;

    @NotEmpty(message = "The model cannot be empty ")
    @Pattern(regexp = "[A-z]+", message = "Car model must be only of letters")
    @Size(max = 12, message = "Quantity letters of car model must be less then 12")
    private String model;

    @PositiveConstraint(message = "Tank capacity must be positive")
    private BigDecimal tankCapacity;

    @PositiveConstraint(message = "Tank capacity must be positive")
    private BigDecimal currentTankCapacity;

    @NotNull(message = "The type fuel is null")
    private Fuel typeFuel;


    @Override
    public void changeResource(BigDecimal countResource) {

    }
}
