package ru.sladkkov.gasstation.model.userdetails.topology.squareelementmap.impl;

import lombok.Data;

@Data
public abstract class TopologyElement {

    private boolean isValidToRide;

    protected TopologyElement(boolean isValidToRide) {
        this.isValidToRide = isValidToRide;
    }

    public TopologyElement() {
    }
}
