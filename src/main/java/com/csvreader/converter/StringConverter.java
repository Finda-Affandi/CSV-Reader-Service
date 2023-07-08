package com.csvreader.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringConverter {
    public void StringToJson(String input) {
        String cleanedInput = input.substring(1, input.length() - 1);

        // Split into individual key-value pairs
        String[] keyValuePairs = cleanedInput.split(",(?=\\w+=)");

        // Create a new map to store the key-value pairs
        Map<String, Object> map = new HashMap<>();

        // Process each key-value pair
        for (String pair : keyValuePairs) {
            // Split into key and value
            String[] parts = pair.split("=");

            // Extract key and value
            String key = parts[0].trim();
            String value = parts[1].trim();

            // Add key-value pair to the map
            map.put(key, value);
        }

        // Print the resulting map
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
