package ru.sladkkov.gasstation.topology.elementmap;

import lombok.Data;
import ru.sladkkov.gasstation.topology.elementmap.impl.Payable;

import java.math.BigDecimal;

@Data
public class BoxOffice implements Payable {

    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(10000);

    private BigDecimal amount;

    @Override
    public void changeResource(BigDecimal countResource) {
       this.amount = this.amount.add(countResource);
    }
}
