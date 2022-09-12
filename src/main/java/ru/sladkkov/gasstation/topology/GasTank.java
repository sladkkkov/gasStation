package ru.sladkkov.gasstation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sladkkov.gasstation.topology.TopologyElement;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GasTank extends TopologyElement {

    private final int numberGasTank;

    private final BigDecimal capacity;
}
