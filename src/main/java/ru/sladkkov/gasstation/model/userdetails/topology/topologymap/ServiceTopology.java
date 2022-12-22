package ru.sladkkov.gasstation.model.userdetails.topology.topologymap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceTopology extends Topology {
    private int countFuelTank;
}
