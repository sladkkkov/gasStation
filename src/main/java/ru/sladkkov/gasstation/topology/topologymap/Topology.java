package ru.sladkkov.gasstation.topology.topologymap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.gasstation.topology.squareelementmap.impl.TopologyElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Topology {

    public static final int MIN_WIDTH_SIZE = 5;
    public static final int MIN_HEIGHT_SIZE = 3;

    public static final int MAX_WIDTH_SIZE = 30;
    public static final int MAX_HEIGHT_SIZE = 14;

    private TopologyElement[][] topologyElements;

    private int widthSize;
    private int heightSize;
}
