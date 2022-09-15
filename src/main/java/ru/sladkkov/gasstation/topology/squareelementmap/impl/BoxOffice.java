package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sladkkov.gasstation.topology.squareelementmap.AbleToDown;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoxOffice extends TopologyElement implements AbleToDown {

    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(10000);

    private BigDecimal amount;

    public BoxOffice(BigDecimal amount) {
        super(false);
        this.amount = amount.subtract(amount);
    }

    @Override
    public void changeResource(BigDecimal countResource) {
       this.amount = this.amount.add(countResource);
    }
}
