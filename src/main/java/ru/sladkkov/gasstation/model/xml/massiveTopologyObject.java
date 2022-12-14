package ru.sladkkov.gasstation.model.xml;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class massiveTopologyObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int topologyLength;
    private int topologyWidth;


}
