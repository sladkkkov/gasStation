package ru.sladkkov.gasstation.controller;

import org.assertj.core.util.Preconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sladkkov.gasstation.dto.TopologyDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/v1/topology")
public class TopologyController {
    // @PostMapping()
    @PostConstruct
    public ResponseEntity<String> topologyCreateController() throws CloneNotSupportedException {//@RequestBody TopologyDto topologyDTO) {

        TopologyDto topologyDTO = new TopologyDto();
        topologyDTO.setTopology(new int[10][10]);
        topologyDTO.setTopologyLength(10);
        topologyDTO.setTopologyWidth(10);
        topologyDTO.setTopologyServiceLength(0);
        int enter = 0;
        int exit = 0;
        int trk = 0;
        int cashBox = 0;
        List<Integer> trkArrayX = new ArrayList<>();
        List<Integer> trkArrayY = new ArrayList<>();
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;


        int[][] topology = topologyDTO.getTopology(); //основная карта
        topology[0][6] = 1;
        topology[0][1] = 2;
        topology[4][3] = 3;
        topology[7][4] = 3;
        topology[3][5] = 3;
        topology[5][7] = 4;

        WaveAlg waveOrig = new WaveAlg(topology.length, topology[0].length - topologyDTO.getTopologyServiceLength());
        for (int i = 0; i < topology.length; i++) {
            int[] ints = topology[i];
            for (int j = 0; j < ints.length; j++) {
                int anInt = ints[j];
                if (anInt == 1) {
                    startX = i;
                    startY = j;
                    enter++;
                }
                if (anInt == 2) {
                    endX = i;
                    endY = j;
                    exit++;
                }
                if (anInt == 3) {
                    trk++;
                    trkArrayX.add(i);
                    trkArrayY.add(i);
                }
                if (anInt == 4)
                    cashBox++;
                if (anInt != 0 && anInt != 1 && anInt != 2) {
                    waveOrig.block(i, j);
                }
            }
        }

        Preconditions.checkState(enter == 1, "Нет въезда для машин.");
        Preconditions.checkState(exit == 1, "Нет выезда для машин.");
        Preconditions.checkState((startX == 0), "Въезд находится не крайних клетках");//|| startX == (topology[0].length-1)
        Preconditions.checkState((endX == 0), "Выезд находится не крайних клетках");//|| endX == (topology[0].length-1)
        Preconditions.checkState(trk < 7 && trk > 0, "Нет ТРК для машин.");
        Preconditions.checkState(cashBox == 1, "Нет кассы для оплаты бензина.");
        String race;
        for (int k = 0; k < trkArrayX.size(); k++) {

            race = waveOrig.findPath(startX + 1, startY + 1, trkArrayX.get(k), trkArrayY.get(k));//сверху
            waveOrig.traceOut();
            race += waveOrig.findPath(trkArrayX.get(k), trkArrayY.get(k), endX + 1, endY + 1);//сверху
            race += (endX + " " + endY);
            waveOrig.traceOut();
            race = race.replace("][", " ,");
            race = race.replace("]", ", ");
            race += "]";
            System.out.println(race);
        }
        return ResponseEntity.ok("Работаем мужики");
    }


}
