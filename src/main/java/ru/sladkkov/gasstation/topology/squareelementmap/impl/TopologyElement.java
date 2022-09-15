package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class TopologyElement {

    private boolean isValidToRide;

    protected TopologyElement(boolean isValidToRide) {
        this.isValidToRide = isValidToRide;
    }
}
