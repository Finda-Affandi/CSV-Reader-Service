package com.csvreader.mapper;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapReader {

    public Map<String, Object> mapping(String fileName) {
        String directoryPath = "src/main/resources/Mapping";

        List<String> columnList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();

        List<Map<String,Object>> mapResult = getMapping(directoryPath, fileName);

        for (Map<String, Object> result : mapResult) {
            map.put(result.get("columnName").toString(), result.get("typeData"));
        }
        return map;
    }

    public static List<Map<String, Object>> getMapping(String path, String filename) {
        String csvFile = path + "/" + filename;
        String line;

        List<Map<String, Object>> mappingList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine(); // Read and discard the header line
            String[] columnNames = line.split(",");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Map<String, Object> dataMap = new HashMap<>();
                for (int i = 0; i < columnNames.length; i++) {
                    dataMap.put(columnNames[i], data[i]);
                }
                mappingList.add(dataMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mappingList;
    }

}
