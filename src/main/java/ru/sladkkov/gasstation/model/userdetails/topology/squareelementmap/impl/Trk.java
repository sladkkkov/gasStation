package ru.sladkkov.gasstation.model.userdetails.topology.squareelementmap.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Trk extends TopologyElement {


    private int fuelPerSecond;

    public Trk(boolean isValidToRide) {
        super(isValidToRide);
    }
}
