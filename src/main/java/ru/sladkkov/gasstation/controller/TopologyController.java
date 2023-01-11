package ru.sladkkov.gasstation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.gasstation.model.Topology;
import ru.sladkkov.gasstation.repository.TopologyRepository;
import ru.sladkkov.gasstation.service.XmlParser;

import java.io.IOException;
import java.util.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin()
@RequestMapping(value = "api/v1/topology")
public class TopologyController {


    private final XmlParser xmlParser;
    private final TopologyRepository topologyRepository;
    Map<String, List<String>> bufferResult;


    @PostMapping()
    public ResponseEntity<String> topologyCreateController(@RequestBody String xmlFile, @RequestParam int length, @RequestParam int width, @RequestParam int lengthService, @RequestParam Long id) throws IOException {//@RequestBody TopologyDto topologyDTO) {

        int[][] topologyElements = xmlParser.parseHashMapToMassiveObject(xmlParser.parseXmlToHashMapOfIdAndTypeObject(xmlFile), length, width);
        int[][] mainTopology = new int[width][length - lengthService];
        int[][] serviceTopology = new int[width][lengthService];
        int l = 0;
        for (int i = 0; i < topologyElements.length; i++) {
            {

                for (int j = 0; j < topologyElements[i].length; j++) {

                    if (j < length - lengthService)
                        mainTopology[i][j] = topologyElements[i][j];
                    else {

                        serviceTopology[i][l] = topologyElements[i][j];
                        l++;
                    }
                }
                l = 0;

            }
        }
        //    TopologyDto topologyDTO = new TopologyDto();
        //    topologyDTO.setTopology(new int[10][10]);
        //   topologyDTO.setTopologyLength(10);
        //    topologyDTO.setTopologyWidth(10);
        //    topologyDTO.setTopologyServiceLength(0);
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


        //  int[][] mainTopology = topologyDTO.getTopology();//Optional.ofNullable(topologyDTO.getTopology()); //основная карта
        //     mainTopology[0][1] = 1;
        //    mainTopology[0][7] = 2;
        //     mainTopology[7][3] = 3;
        //     mainTopology[7][7] = 3;
        //     mainTopology[3][5] = 3;
        //    mainTopology[4][7] = 4;

        WaveAlg waveOrig = new WaveAlg(mainTopology.length + 1, mainTopology[0].length + 1);
        for (int i = 0; i < mainTopology.length; i++) {
            int[] ints = mainTopology[i];
            for (int j = 0; j < ints.length; j++) {
                int anInt = ints[j];
                if (anInt == 1) {
                    startX = j;
                    startY = i;
                    enter++;
                }
                if (anInt == 2) {
                    endX = j;
                    endY = i;
                    exit++;
                }
                if (anInt == 3) {
                    trk++;
                    Preconditions.checkState(!(i == 0 || j == 0 || i == mainTopology.length - 1 || j == ints.length - 1), "ТРК должны быть расположены не на границах топологии.");
                    Preconditions.checkState(!(j < startX || j > endX), "ТРК должны быть расположены между въездом и выездом.");
                    trkArrayX.add(j);
                    trkArrayY.add(i);
                }
                if (anInt == 4)
                    cashBox++;
                if (anInt == 5) {
                    Preconditions.checkState(false, "Топливный бак не должен находится в служебной зоне");
                }
                if (anInt != 0) {

                    if (j == 0 && i == 0) {
                        Preconditions.checkState((mainTopology[i + 1][j + 1] == 0) &&
                                        (mainTopology[i + 1][j] == 0) &&
                                        (mainTopology[i][j + 1] == 0),
                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                    } else {
                        if (i == mainTopology.length - 1 && j == 0) {
                            Preconditions.checkState((mainTopology[i][j + 1] == 0) &&
                                            (mainTopology[i - 1][j + 1] == 0) &&
                                            (mainTopology[i - 1][j] == 0),
                                    "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                        } else {
                            if (i == 0 && j == mainTopology[i].length - 1) {
                                Preconditions.checkState((mainTopology[i + 1][j] == 0) &&
                                                (mainTopology[i + 1][j - 1] == 0) &&
                                                (mainTopology[i][j - 1] == 0),
                                        "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                            } else {
                                if (j == 0) {
                                    Preconditions.checkState((mainTopology[i + 1][j + 1] == 0) &&
                                                    (mainTopology[i + 1][j] == 0) &&
                                                    (mainTopology[i][j + 1] == 0) &&
                                                    (mainTopology[i - 1][j + 1] == 0) &&
                                                    (mainTopology[i - 1][j] == 0),
                                            "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");

                                } else {
                                    if (i == 0) {

                                        Preconditions.checkState((mainTopology[i + 1][j + 1] == 0) &&
                                                (mainTopology[i + 1][j] == 0) &&
                                                (mainTopology[i + 1][j - 1] == 0) &&
                                                (mainTopology[i][j + 1] == 0) &&
                                                (mainTopology[i][j - 1] == 0), "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                    } else if (i == mainTopology.length - 1 && j == mainTopology[i].length - 1) {
                                        Preconditions.checkState(
                                                (mainTopology[i][j - 1] == 0) &&
                                                        (mainTopology[i - 1][j] == 0) &&
                                                        (mainTopology[i - 1][j - 1] == 0),
                                                "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");

                                    } else {
                                        if (i == mainTopology.length - 1) {
                                            Preconditions.checkState(
                                                    (mainTopology[i][j + 1] == 0) &&
                                                            (mainTopology[i][j - 1] == 0) &&
                                                            (mainTopology[i - 1][j + 1] == 0) &&
                                                            (mainTopology[i - 1][j] == 0) &&
                                                            (mainTopology[i - 1][j - 1] == 0),
                                                    "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                        } else {
                                            if (j == mainTopology[i].length - 1) {
                                                Preconditions.checkState(
                                                        (mainTopology[i + 1][j] == 0) &&
                                                                (mainTopology[i + 1][j - 1] == 0) &&
                                                                (mainTopology[i][j - 1] == 0) &&
                                                                (mainTopology[i - 1][j] == 0) &&
                                                                (mainTopology[i - 1][j - 1] == 0),
                                                        "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                            } else {
                                                Preconditions.checkState((mainTopology[i + 1][j + 1] == 0) &&
                                                                (mainTopology[i + 1][j] == 0) &&
                                                                (mainTopology[i + 1][j - 1] == 0) &&
                                                                (mainTopology[i][j + 1] == 0) &&
                                                                (mainTopology[i][j - 1] == 0) &&
                                                                (mainTopology[i - 1][j + 1] == 0) &&
                                                                (mainTopology[i - 1][j] == 0) &&
                                                                (mainTopology[i - 1][j - 1] == 0),
                                                        "Элементы топологии должны располагаться как минимум в одной клетке друг от друга");
                                            }
                                        }
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
        Preconditions.checkState((startY == 0), "Въезд находится не крайних клетках");//|| startX == (topology[0].length-1)
        Preconditions.checkState((endY == 0), "Выезд находится не крайних клетках");//|| endX == (topology[0].length-1)
        Preconditions.checkState(trk < 7 && trk > 0, "Нет ТРК для машин.");
        Preconditions.checkState(cashBox == 1, "Нет кассы для оплаты бензина.");
        String race = "";
        List<String> raceList = new ArrayList<>();
        for (int k = 0; k < trkArrayX.size(); k++) {
            WaveAlg waveCopy = new WaveAlg(mainTopology.length + 2, mainTopology[0].length + 2);
            for (int i = 0; i < mainTopology.length; i++) {
                int[] ints = mainTopology[i];
                for (int j = 0; j < ints.length; j++) {
                    int anInt = ints[j];
                    if (anInt != 0 && anInt != 1 && anInt != 2) {
                        if (!(i == trkArrayY.get(k) && j == trkArrayX.get(k))) {
                            waveCopy.block(j + 1, i + 1);
                        }
                    }
                    if (i == 0 && anInt == 0) {
                        waveCopy.block(j + 1, i + 1);
                    }
                }
            }
            race = waveCopy.findPath(startX + 1, startY + 1, trkArrayX.get(k) + 1, trkArrayY.get(k) + 1, topologyElements[0].length);//сверху
            race += waveCopy.findPath(trkArrayX.get(k) + 1, trkArrayY.get(k) + 1, endX + 1, endY + 1, topologyElements[0].length);//сверху
            race += (endX + endY * topologyElements[0].length);
            waveCopy.traceOut();
            race = race.replace("][", " ,");
            race = race.replace("]", ", ");
            race += "]";
            raceList.add(race);
            System.out.println(race);
            waveCopy = null;
        }

        Map<String, List<String>> mapResult = new HashMap<>();
        mapResult.put("main", raceList);
        System.out.println(raceList);
        mapResult.put("service", serviceTopology(serviceTopology, topologyElements));
        bufferResult = mapResult;
        Topology topology = new Topology();
        topology.setId(id);
        topology.setWidth(width);
        topology.setLength(length);
        topology.setXmlTopology(xmlFile);
        topology.setLengthService(lengthService);

        ObjectMapper objectMapper = new ObjectMapper();
        System.err.println(objectMapper.writeValueAsString(mapResult).replaceAll("\\[\"\\[", "[[").replaceAll("]\"]", "]]").replaceAll("\",\"", ","));
        topology.setRoutes(objectMapper.writeValueAsString(mapResult).replaceAll("\\[\"\\[", "[[").replaceAll("]\"]", "]]").replaceAll("\",\"", ","));
        topologyRepository.save(topology);

        //todo toplogyRepository.save(topologyXml)
        return ResponseEntity.ok(objectMapper.writeValueAsString(mapResult).replaceAll("\\[\"\\[", "[[").replaceAll("]\"]", "]]").replaceAll("\",\"", ","));
    }

    public List<String> serviceTopology(int[][] topology, int[][] allTopology) {

        int baks = 0;
        ArrayList<Integer> bakArrayX = new ArrayList<>();
        ArrayList<Integer> bakArrayY = new ArrayList<>();
        int startX = 0;
        int startY = 0;
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
                    startX = j;
                    startY = i;
                }
                if (anInt == 4)
                    Preconditions.checkState(false, "Касса не должна находится в служебной зоне");
                if (anInt == 5) {
                    baks++;
                    bakArrayX.add(j);
                    bakArrayY.add(i);

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
            WaveAlg waveCopy = new WaveAlg(topology.length + 2, topology[0].length + 2);
            for (int i = 0; i < topology.length; i++) {
                int[] ints = topology[i];
                for (int j = 0; j < ints.length; j++) {
                    int anInt = ints[j];
                    if (anInt != 0 && anInt != 1 && anInt != 2 && (anInt != bakArrayY.get(k) && j != bakArrayX.get(k))) {
                        waveCopy.block(j + 1, i + 1);
                    }
                }
            }
            race = waveCopy.findPathService(startX + 1, startY + 1, bakArrayX.get(k) + 1, bakArrayY.get(k) + 1, allTopology[0].length, topology[0].length);//сверху
            race += waveCopy.findPathService(bakArrayX.get(k) + 1, bakArrayY.get(k) + 1, startX + 1, startY + 1, allTopology[0].length, topology[0].length);//сверху
            race += (startX + allTopology[0].length - topology[0].length + startY * allTopology[0].length);
            waveCopy.traceOut();
            race = race.replace("][", " ,");
            race = race.replace("]", ", ");
            race += "]";
            raceList.add(race);
            System.out.println(race);
            waveCopy = null;
        }
        System.out.println(raceList);
        return raceList;
    }

    @PostMapping("/parse/xml/{length}/{width}")
    public ResponseEntity<String> parseXmlToMassiveObject(@RequestBody String xmlFile, @PathVariable int length,
                                                          @PathVariable int width) throws IOException {
        int[][] topologyElements = xmlParser.parseHashMapToMassiveObject(xmlParser.parseXmlToHashMapOfIdAndTypeObject(xmlFile), length, width);
        return ResponseEntity.ok(Arrays.deepToString(topologyElements));
    }

    @GetMapping("/routes")
    public ResponseEntity<Topology> getRoute(@RequestParam Long id) {
        return ResponseEntity.ok(topologyRepository.findById(id).get());
    }

    @GetMapping("/routes/all")
    public ResponseEntity<List<Topology>> getRoutes() {
        return ResponseEntity.ok().body(topologyRepository.findAll());
    }
}
