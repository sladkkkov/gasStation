package ru.sladkkov.gasstation.topology.elementmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sladkkov.gasstation.topology.elementmap.impl.Payable;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class GasTank extends TopologyElement implements Payable {

    public static final BigDecimal CAPACITY = BigDecimal.valueOf(10_000);

    private final int numberGasTank;

    private BigDecimal amountOfRemainingFuel;

    @Override
    public void changeResource(BigDecimal countResource) {

    }
}
