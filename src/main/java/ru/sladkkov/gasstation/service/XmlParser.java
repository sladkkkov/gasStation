package ru.sladkkov.gasstation.service;

import ru.sladkkov.gasstation.topology.squareelementmap.impl.*;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {

    final String regex = "<div id=\"(\\d+)\" class=\"(\\w+)\" style=\"[^\"]*\"></div>";
    final Pattern pattern = Pattern.compile(regex);

    public HashMap<Integer, TopologyElement> parseXmlToHashMapOfIdAndTypeObject(String xmlDocument, int length, int width) {
        Matcher matcher = pattern.matcher(xmlDocument);
        HashMap<Integer, TopologyElement> hashMap = new HashMap<>();

        while (matcher.find()) {
            hashMap.put(Integer.valueOf(matcher.group(1)),
                    switch (matcher.group(2)) {
                        case "trk" -> new Trk(false);
                        case "kassa" -> new BoxOffice(false);
                        case "bak" -> new GasTank(false);
                        case "inLogo" -> new InTopology(true);
                        case "outLogo" -> new OutTopology(true);
                        case "govno" -> new FreeElement(true);
                    });
        }
        return hashMap;
    }

    public TopologyElement[][] parseHashMapToMassiveObject(HashMap<Integer, TopologyElement> hashMapWithObject, int length, int width) {
        TopologyElement[][] topologyElements = new TopologyElement[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {

                if (i == 0) {
                    topologyElements[i][j] = hashMapWithObject.get(j);
                } else {
                    topologyElements[i][j] = hashMapWithObject.get(i * 10 + j);
                }
            }
        }

        return topologyElements;
    }
}
