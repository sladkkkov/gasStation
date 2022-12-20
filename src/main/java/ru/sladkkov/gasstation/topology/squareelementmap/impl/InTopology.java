package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
@Data

public class InTopology extends TopologyElement {
    public InTopology(boolean isValidToRide) {
        super(isValidToRide);
    }
}
