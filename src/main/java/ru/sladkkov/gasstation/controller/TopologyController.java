package ru.sladkkov.gasstation.controller;

import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.gasstation.dto.TopologyDto;
import ru.sladkkov.gasstation.service.XmlParser;
import ru.sladkkov.gasstation.topology.squareelementmap.impl.TopologyElement;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.annotation.ElementType.FIELD;


@RestController
@RequestMapping(value = "api/v1/topology")
public class TopologyController {


    private final XmlParser xmlParser;

    @Autowired
    public TopologyController(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    // @PostMapping()
    @PostConstruct

    public ResponseEntity<List<String>> topologyCreateController() {//@RequestBody TopologyDto topologyDTO) {
//todo parser mainToplogy[][] and serviceTopogy[][]
        TopologyDto topologyDTO = new TopologyDto();
        topologyDTO.setTopology(new int[10][10]);
        topologyDTO.setTopologyLength(10);
        topologyDTO.setTopologyWidth(10);
        topologyDTO.setTopologyServiceLength(0);
        int enter = 0;//1
        int exit = 0;//2
        int trk = 0;//3
        int cashBox = 0;//4
        List<Integer> trkArrayX = new ArrayList<>();
        List<Integer> trkArrayY = new ArrayList<>();
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;


        int[][] topology = topologyDTO.getTopology();//Optional.ofNullable(topologyDTO.getTopology()); //основная карта
        topology[0][1] = 1;
        topology[0][7] = 2;
        topology[7][3] = 3;
        topology[7][7] = 3;
        topology[3][5] = 3;
        topology[4][7] = 4;

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
                    Preconditions.checkState(!(i == 0 || j == 0 || i == topology.length - 1 || j == ints.length - 1), "ТРК должны быть расположены не на границах топологии.");
                    trkArrayX.add(i);
                    trkArrayY.add(j);
                }
                if (anInt == 4)
                    cashBox++;
                if (anInt != 0) {

                    if (j == 0 && i == 0) {
                        Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                        (topology[i + 1][j] == 0) &&
                                        (topology[i][j + 1] == 0),
                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                    } else {
                        if (j == 0) {
                            Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                            (topology[i + 1][j] == 0) &&
                                            (topology[i][j + 1] == 0) &&
                                            (topology[i - 1][j + 1] == 0) &&
                                            (topology[i - 1][j] == 0),
                                    "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");

                        } else {
                            if (i == 0) {

                                Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                        (topology[i + 1][j] == 0) &&
                                        (topology[i + 1][j - 1] == 0) &&
                                        (topology[i][j + 1] == 0) &&
                                        (topology[i][j - 1] == 0), "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                            } else if (i == topology.length - 1 && j == topology[i].length - 1) {
                                Preconditions.checkState(
                                        (topology[i][j - 1] == 0) &&
                                                (topology[i - 1][j] == 0) &&
                                                (topology[i - 1][j - 1] == 0),
                                        "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");

                            } else {
                                if (i == topology.length - 1) {
                                    Preconditions.checkState(
                                            (topology[i][j + 1] == 0) &&
                                                    (topology[i][j - 1] == 0) &&
                                                    (topology[i - 1][j + 1] == 0) &&
                                                    (topology[i - 1][j] == 0) &&
                                                    (topology[i - 1][j - 1] == 0),
                                            "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                } else {
                                    if (j == topology[i].length - 1) {
                                        Preconditions.checkState(
                                                (topology[i + 1][j] == 0) &&
                                                        (topology[i + 1][j - 1] == 0) &&
                                                        (topology[i][j - 1] == 0) &&
                                                        (topology[i - 1][j] == 0) &&
                                                        (topology[i - 1][j - 1] == 0),
                                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                    } else {
                                        Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                                        (topology[i + 1][j] == 0) &&
                                                        (topology[i + 1][j - 1] == 0) &&
                                                        (topology[i][j + 1] == 0) &&
                                                        (topology[i][j - 1] == 0) &&
                                                        (topology[i - 1][j + 1] == 0) &&
                                                        (topology[i - 1][j] == 0) &&
                                                        (topology[i - 1][j - 1] == 0),
                                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(i + "  " + j);

                }
            }


        }
        waveOrig = null;
        Preconditions.checkState(enter == 1, "Нет въезда для машин.");
        Preconditions.checkState(exit == 1, "Нет выезда для машин.");
        Preconditions.checkState((startX == 0), "Въезд находится не крайних клетках");//|| startX == (topology[0].length-1)
        Preconditions.checkState((endX == 0), "Выезд находится не крайних клетках");//|| endX == (topology[0].length-1)
        Preconditions.checkState(trk < 7 && trk > 0, "Нет ТРК для машин.");
        Preconditions.checkState(cashBox == 1, "Нет кассы для оплаты бензина.");
        String race = "";
        List<String> raceList = new ArrayList<>();
        for (int k = 0; k < trkArrayX.size(); k++) {
            WaveAlg waveCopy = new WaveAlg(topology.length, topology[0].length - topologyDTO.getTopologyServiceLength());
            for (int i = 0; i < topology.length; i++) {
                int[] ints = topology[i];
                for (int j = 0; j < ints.length; j++) {
                    int anInt = ints[j];
                    if (anInt != 0 && anInt != 1 && anInt != 2 && (anInt != trkArrayX.get(k) && j != trkArrayY.get(k))) {
                        waveCopy.block(i, j);
                    }
                }
            }
            race = waveCopy.findPath(startX + 1, startY + 1, trkArrayX.get(k), trkArrayY.get(k));//сверху
            race += waveCopy.findPath(trkArrayX.get(k), trkArrayY.get(k), endX + 1, endY + 1);//сверху
            race += (endX + " " + endY);
            waveCopy.traceOut();
            race = race.replace("][", " ,");
            race = race.replace("]", ", ");
            race += "]";
            raceList.add(race);
            System.out.println(race);
            waveCopy = null;
        }
        System.out.println(raceList);
        serviceTopology(topology);
        return ResponseEntity.ok(raceList);
    }

    public List<String> serviceTopology( int[][] topology) {

        int baks = 0;
        ArrayList<Integer> bakArrayX = new ArrayList<>();
        ArrayList<Integer> bakArrayY = new ArrayList<>();
        int startX=0;
        int startY=0;
        for (int i = 0; i < topology.length; i++) {
            int[] ints = topology[i];
            for (int j = 0; j < ints.length; j++) {
                int anInt = ints[j];
                if (anInt == 1) {
                    Preconditions.checkState(false, "Въезд не должен находится в служебной зоне");
                }
                if (anInt == 2) {
                    Preconditions.checkState(false, "Выезд не должен находится в служебной зоне");
                }
                if (anInt == 3) {
                    Preconditions.checkState(false, "ТРК не должна находится в служебной зоне");
                }
                if (anInt == 7) {
                 startX=i;
                 startY=j;
                }
                if (anInt == 4)
                    Preconditions.checkState(false, "Касса не должна находится в служебной зоне");
                if (anInt == 5) {
                    baks++;
                    bakArrayX.add(i);
                    bakArrayY.add(j);

                    if (j == 0 && i == 0) {
                        Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                        (topology[i + 1][j] == 0) &&
                                        (topology[i][j + 1] == 0),
                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                    } else {
                        if (j == 0) {
                            Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                            (topology[i + 1][j] == 0) &&
                                            (topology[i][j + 1] == 0) &&
                                            (topology[i - 1][j + 1] == 0) &&
                                            (topology[i - 1][j] == 0),
                                    "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");

                        } else {
                            if (i == 0) {

                                Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                        (topology[i + 1][j] == 0) &&
                                        (topology[i + 1][j - 1] == 0) &&
                                        (topology[i][j + 1] == 0) &&
                                        (topology[i][j - 1] == 0), "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                            } else if (i == topology.length - 1 && j == topology[i].length - 1) {
                                Preconditions.checkState(
                                        (topology[i][j - 1] == 0) &&
                                                (topology[i - 1][j] == 0) &&
                                                (topology[i - 1][j - 1] == 0),
                                        "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");

                            } else {
                                if (i == topology.length - 1) {
                                    Preconditions.checkState(
                                            (topology[i][j + 1] == 0) &&
                                                    (topology[i][j - 1] == 0) &&
                                                    (topology[i - 1][j + 1] == 0) &&
                                                    (topology[i - 1][j] == 0) &&
                                                    (topology[i - 1][j - 1] == 0),
                                            "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                } else {
                                    if (j == topology[i].length - 1) {
                                        Preconditions.checkState(
                                                (topology[i + 1][j] == 0) &&
                                                        (topology[i + 1][j - 1] == 0) &&
                                                        (topology[i][j - 1] == 0) &&
                                                        (topology[i - 1][j] == 0) &&
                                                        (topology[i - 1][j - 1] == 0),
                                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                    } else {
                                        Preconditions.checkState((topology[i + 1][j + 1] == 0) &&
                                                        (topology[i + 1][j] == 0) &&
                                                        (topology[i + 1][j - 1] == 0) &&
                                                        (topology[i][j + 1] == 0) &&
                                                        (topology[i][j - 1] == 0) &&
                                                        (topology[i - 1][j + 1] == 0) &&
                                                        (topology[i - 1][j] == 0) &&
                                                        (topology[i - 1][j - 1] == 0),
                                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(i + "  " + j);

                }
            }


        }
        Preconditions.checkState(baks < 7 && baks > 0, "Колличество баков должно быть от 0 до 7");
        String race;
        List<String> raceList = new ArrayList<>();
        for (int k = 0; k < bakArrayX.size(); k++) {
            WaveAlg waveCopy = new WaveAlg(topology.length, topology[0].length);
            for (int i = 0; i < topology.length; i++) {
                int[] ints = topology[i];
                for (int j = 0; j < ints.length; j++) {
                    int anInt = ints[j];
                    if (anInt != 0 && anInt != 1 && anInt != 2 && (anInt != bakArrayY.get(k) && j != bakArrayY.get(k))) {
                        waveCopy.block(i, j);
                    }
                }
            }
            race = waveCopy.findPath(startX + 1, startY + 1, bakArrayX.get(k), bakArrayY.get(k));//сверху
            race += waveCopy.findPath(bakArrayX.get(k), bakArrayY.get(k), startX + 1, startY + 1);//сверху
            race += (startX + " " + startY);
            waveCopy.traceOut();
            race = race.replace("][", " ,");
            race = race.replace("]", ", ");
            raceList.add(race);
            System.out.println(race);
            waveCopy = null;
        }
        System.out.println(raceList);
        return raceList;
    }






@GetMapping("/parse/xml/{length}/{width}")
public ResponseEntity<String> parseXmlToMassiveObject(@RequestBody String xmlFile,@PathVariable int length,@PathVariable int width)throws IOException{
        TopologyElement[][]topologyElements=xmlParser.parseHashMapToMassiveObject(xmlParser.parseXmlToHashMapOfIdAndTypeObject(xmlFile),length,width);
        return ResponseEntity.ok(Arrays.deepToString(topologyElements));
        }
        }
