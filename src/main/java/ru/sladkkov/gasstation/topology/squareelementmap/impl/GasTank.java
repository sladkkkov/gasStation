package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sladkkov.gasstation.topology.squareelementmap.AbleToDown;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class GasTank extends TopologyElement implements AbleToDown {

    public static final BigDecimal CAPACITY = BigDecimal.valueOf(10_000);

    private BigDecimal amountOfRemainingFuel;

    public GasTank(boolean isValidToRide) {
        super(isValidToRide);
    }


    @Override
    public void changeResource(BigDecimal countResource) {
        this.amountOfRemainingFuel = amountOfRemainingFuel.subtract(countResource);
    }
}
