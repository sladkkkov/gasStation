package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OutTopology extends TopologyElement {

    public OutTopology(boolean isValidToRide) {
        super(isValidToRide);
    }
}
