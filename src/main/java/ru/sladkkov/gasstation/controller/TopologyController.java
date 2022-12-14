package ru.sladkkov.gasstation.controller;

import org.assertj.core.util.Preconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sladkkov.gasstation.dto.TopologyDto;


@RestController
@RequestMapping(value = "api/v1/topology")
public class TopologyController {
    @PostMapping()
    public ResponseEntity<String> topologyCreateController(@RequestBody TopologyDto topologyDTO) {

        boolean enter = false;
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        boolean exit = false;
        boolean trk = false;
        boolean cashBox = false;

        int[][] topology = topologyDTO.getTopology();
        WaveAlg wave = new WaveAlg(topology.length, topology[0].length - topologyDTO.getTopologyServiceLength());
        for (int i = 0; i < topology.length; i++) {
            int[] ints = topology[i];
            for (int j = 0; j < ints.length; j++) {
                int anInt = ints[j];
                if (anInt == 1) {
                    startX = i;
                    startY = j;
                    enter = true;
                }
                if (anInt == 2) {
                    endX = i;
                    endY = j;
                    exit = true;
                }
                if (anInt == 3)
                    trk = true;
                if (anInt == 4)
                    cashBox = true;
                if (anInt != 0 && anInt != 1 && anInt != 2) {
                    wave.block(i, j);
                }
            }

        }
        Preconditions.checkArgument(enter, "Нет въезда для машин.");
        Preconditions.checkArgument(exit, "Нет выезда для машин.");
        Preconditions.checkArgument(trk, "Нет ТРК для машин.");
        Preconditions.checkArgument(cashBox, "Нет кассы для оплаты бензина.");
        wave.findPath(startX, startY, endX, endY);
        wave.traceOut();

        return ResponseEntity.ok("Работаем мужики");
    }
}
