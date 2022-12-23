package ru.sladkkov.gasstation.service;

import org.springframework.stereotype.Service;
import ru.sladkkov.gasstation.topology.squareelementmap.impl.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class XmlParser {

    private static final String REGEX = "<div id=\"(\\d+)\" class=\"(\\w+)\" style=\"[^\"]*\"></div>";
    final Pattern pattern = Pattern.compile(REGEX);

    public Map<Integer, Integer> parseXmlToHashMapOfIdAndTypeObject(String xmlDocument) throws IOException {
        Matcher matcher = pattern.matcher(xmlDocument);
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        while (matcher.find()) {
            hashMap.put(Integer.valueOf(matcher.group(1)),
                    switch (matcher.group(2)) {
                        case "trk" -> 3;
                        case "kassa" -> 4;
                        case "bak" -> 5;
                        case "inLogo" -> 1;
                        case "outLogo" -> 2;
                        case "govno" -> 0;
                        case "inService" -> 7;
                        default -> throw new IOException();
                    });
        }
        return hashMap;
    }

    public int[][] parseHashMapToMassiveObject(Map<Integer, Integer> hashMapWithObject, int length, int width) {
        int[][] topologyElements = new int[length][width];

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
