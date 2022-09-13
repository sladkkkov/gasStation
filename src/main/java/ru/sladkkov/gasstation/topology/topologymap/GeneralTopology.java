package ru.sladkkov.gasstation.topology.topologymap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GeneralTopology extends Topology{
    private int countTrk;
}
