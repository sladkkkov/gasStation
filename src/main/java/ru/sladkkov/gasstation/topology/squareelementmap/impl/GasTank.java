package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sladkkov.gasstation.topology.squareelementmap.AbleToDown;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class GasTank extends TopologyElement implements AbleToDown {

    public static final BigDecimal CAPACITY = BigDecimal.valueOf(10_000);

    private final int numberGasTank;

    private BigDecimal amountOfRemainingFuel;

    public GasTank(int numberGasTank) {
        super(false);
        this.numberGasTank = numberGasTank;
        this.amountOfRemainingFuel = CAPACITY;
    }

    @Override
    public void changeResource(BigDecimal countResource) {
        this.amountOfRemainingFuel = amountOfRemainingFuel.subtract(countResource);
    }
}
