package com.csvreader.jsonbuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class JsonBuilder {
    public static String generateJsonData(List<List<Map<String, Object>>> jsonDataList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String separator = "";

        for (List<Map<String, Object>> jsonData : jsonDataList) {
            for (Map<String, Object> data : jsonData) {
                Map<String, Object> modifiedData = new HashMap<>();
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    String type = getType(value); // Mendapatkan tipe data dari nilai

                    // Menggabungkan tipe data dengan nilai dalam bentuk objek JSON
                    Map<String, Object> jsonField = new LinkedHashMap<>();
                    jsonField.put("value", removeQuotes(value));
                    jsonField.put("type", type);

                    modifiedData.put(key, jsonField);
                }

                String jsonStr = gson.toJson(modifiedData);
                sb.append(separator).append(jsonStr);
                separator = ", ";
            }
        }

        sb.append("]");
        System.out.println(sb.toString());
        return sb.toString();
    }

    private static String getType(Object value) {
        if (value instanceof String) {
            return "string";
        } else if (value instanceof Number) {
            return "number";
        } else if (value instanceof Date) {
            return "date";
        } else {
            return "unknown";
        }
    }

    private static String removeQuotes(Object value) {
        if (value instanceof String) {
            String stringValue = (String) value;
            if (stringValue.startsWith("\"") && stringValue.endsWith("\"")) {
                return stringValue.substring(1, stringValue.length() - 1);
            }
        }
        return value.toString();
    }
}
