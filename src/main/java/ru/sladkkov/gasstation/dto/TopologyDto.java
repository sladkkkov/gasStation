package ru.sladkkov.gasstation.dto;

import lombok.Data;


@Data
public class TopologyDto {
    int[][] topology;
    int topologyLength;
    int topologyWidth;
    int topologyService;
}
