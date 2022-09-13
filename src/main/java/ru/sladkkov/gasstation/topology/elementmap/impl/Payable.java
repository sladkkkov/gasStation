package ru.sladkkov.gasstation.topology.elementmap.impl;

import java.math.BigDecimal;

public interface Payable {
    void changeResource(BigDecimal countResource);
}
