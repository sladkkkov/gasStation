package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
public class Trk extends TopologyElement {


    private int fuelPerSecond;

    public Trk(boolean isValidToRide) {
        super(isValidToRide);
    }
}
