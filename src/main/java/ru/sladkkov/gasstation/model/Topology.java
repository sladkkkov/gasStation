package ru.sladkkov.gasstation.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name = "topology")
public class Topology {
    @Id
    protected Long id;
    private String xmlTopology;
    private Integer length;
    private Integer width;
    private Integer lengthService;
    private String routes;

}
