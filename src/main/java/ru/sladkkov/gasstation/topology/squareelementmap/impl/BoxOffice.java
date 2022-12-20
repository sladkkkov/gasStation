package ru.sladkkov.gasstation.topology.squareelementmap.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoxOffice extends TopologyElement {

    public BoxOffice(boolean isValidToRide) {
        super(isValidToRide);
    }


}
