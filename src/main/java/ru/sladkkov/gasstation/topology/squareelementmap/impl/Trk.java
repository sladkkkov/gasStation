package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Trk extends TopologyElement {

    private final int countFuelingGun;

    private int fuelPerSecond;

    public Trk(boolean isValidToRide, int countFuelingGun, int fuelPerSecond) {
        super(isValidToRide);
        this.countFuelingGun = countFuelingGun;
        this.fuelPerSecond = fuelPerSecond;
    }
}
