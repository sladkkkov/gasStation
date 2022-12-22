package ru.sladkkov.gasstation.model.userdetails.topology.squareelementmap.impl;

import lombok.Data;

@Data
public class OutTopology extends TopologyElement {

    public OutTopology(boolean isValidToRide) {
        super(isValidToRide);
    }
}
