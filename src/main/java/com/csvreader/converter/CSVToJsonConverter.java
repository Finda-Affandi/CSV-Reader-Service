package com.csvreader.converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.csvreader.restapiclient.RestApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVToJsonConverter {
    public static void Post() {
        String csvFolderPath = "D:\\Downloads\\Compressed\\Data-7\\Data-7\\";
        List<String> csvFiles = listCsvFiles(csvFolderPath);

        String postgreSQLUrl = "http://localhost:8080/api/postgres";
        String cassandraUrl = "http://localhost:8081/api/cassandra";
        RestApiClient restApiClient = new RestApiClient();

        for (String csvFile : csvFiles) {
            String csvFilePath = csvFolderPath + csvFile;
            List<String[]> csvData = readCsvFile(csvFilePath);
            List<Map<String, String>> jsonData = convertToJSON(csvData);
            String body = generateJsonData(Collections.singletonList(jsonData));
            String header = getLimitedTitle(csvFile);

            try {
                restApiClient.Post(postgreSQLUrl, header, body);
            } catch (Exception e) {
                System.err.println("Error calling PostgreSQL endpoint: " + e.getMessage());
            }

            try {
                restApiClient.Post(cassandraUrl, header, body);
            } catch (Exception e) {
                System.err.println("Error calling Cassandra endpoint: " + e.getMessage());
            }
        }
    }

    public static List<String> listCsvFiles(String csvFolderPath) {
        List<String> csvFiles = new ArrayList<>();
        try {
            java.io.File folder = new java.io.File(csvFolderPath);
            java.io.File[] files = folder.listFiles();
            for (java.io.File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    csvFiles.add(file.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    private static List<String[]> readCsvFile(String csvFilePath) {
        List<String[]> csvData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                csvData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvData;
    }

    private static List<Map<String, String>> convertToJSON(List<String[]> csvData) {
        List<Map<String, String>> jsonData = new ArrayList<>();
        String[] headers = csvData.get(0);
        for (int i = 1; i < csvData.size(); i++) {
            String[] values = csvData.get(i);
            Map<String, String> data = new HashMap<>();
            for (int j = 0; j < headers.length; j++) {
                data.put(headers[j], values[j]);
            }
            jsonData.add(data);
        }
        return jsonData;
    }

    public static String generateJsonData(List<List<Map<String, String>>> jsonDataList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (List<Map<String, String>> jsonData : jsonDataList) {
            sb.append(separator);
            sb.append("[");
            String innerSeparator = "";
            for (Map<String, String> data : jsonData) {
                Map<String, String> modifiedData = new HashMap<>();
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                        value = value.replace(" ", "");
                    }
                    modifiedData.put(key, value);
                }
                String jsonStr = gson.toJson(modifiedData);
                sb.append(innerSeparator).append(jsonStr);
                innerSeparator = ", ";
            }
            sb.append("]");
            separator = ", ";
        }
//        System.out.println(sb);
        return sb.toString();
    }

    public static String getLimitedTitle(String filename) {
        // Mendapatkan nama file tanpa ekstensi
        return filename.substring(0, filename.lastIndexOf("_"));
    }
}