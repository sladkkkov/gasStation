package ru.sladkkov.gasstation.model.userdetails.topology.squareelementmap.impl;

import lombok.Data;

@Data
public class FreeElement extends TopologyElement{
    public FreeElement(boolean isValidToRide) {
        super(isValidToRide);
    }
}
