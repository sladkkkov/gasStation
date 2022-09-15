package ru.sladkkov.gasstation.topology.freemapelement;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sladkkov.gasstation.validation.PositiveConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Fuel {

    @Size(min = 2, max = 30, message = "The length of the fuel name should be from 2 to 30 characters")
    @NotEmpty(message = "The fuel name should not be empty")
    @Pattern(regexp = "[A-z]+", message = "The fuel name must consist of Latin letters")
    private String name;

    @NotNull(message = "The fuel price is 0")
    @PositiveConstraint
    private BigDecimal price;
}
