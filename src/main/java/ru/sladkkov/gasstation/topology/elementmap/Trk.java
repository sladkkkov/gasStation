package ru.sladkkov.gasstation.topology.elementmap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trk extends TopologyElement {

    private final int countFuelingGun ;

    private int fuelPerSecond;
}
