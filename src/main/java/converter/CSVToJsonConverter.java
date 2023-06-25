package converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CSVToJsonConverter {

    public static void main(String[] args) {
        String csvFolderPath = "D:\\2. Kuliah\\3. Tahun 3\\3. Semester 9\\Pengujian Sistem\\TAS\\Data-7\\"; // Ubah dengan path folder tempat file CSV berada

        List<List<Map<String, String>>> jsonDataList = processCsvFiles(csvFolderPath);

        generateJsonData(jsonDataList);
    }

    public static List<List<Map<String, String>>> processCsvFiles(String csvFolderPath) {
        List<List<Map<String, String>>> jsonDataList = new ArrayList<>();
        List<String> csvFiles = listCsvFiles(csvFolderPath);

        for (String csvFile : csvFiles) {
            List<String[]> csvData = readCsvFile(csvFolderPath + "/" + csvFile);
            List<Map<String, String>> jsonData = convertToJSON(csvData);
            jsonDataList.add(jsonData);
        }

        return jsonDataList;
    }

    private static List<String> listCsvFiles(String csvFolderPath) {
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
        sb.append("[");
        String separator = "";
        for (List<Map<String, String>> jsonData : jsonDataList) {
            for (Map<String, String> data : jsonData) {
                Map<String, String> modifiedData = new HashMap<>();
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    modifiedData.put(key, value);
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


}










//package converter;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//public class CSVToJsonConverter {
//
//    public static void main(String[] args) {
//        String csvFolderPath = "D:\\2. Kuliah\\3. Tahun 3\\3. Semester 9\\Pengujian Sistem\\TAS\\Data-7\\"; // Ubah dengan path folder tempat file CSV berada
//
//        List<List<Map<String, Object>>> jsonDataList = processCsvFiles(csvFolderPath);
//
//        generateJsonData(jsonDataList);
//    }
//
//    public static List<List<Map<String, Object>>> processCsvFiles(String csvFolderPath) {
//        List<List<Map<String, Object>>> jsonDataList = new ArrayList<>();
//        List<String> csvFiles = listCsvFiles(csvFolderPath);
//
//        for (String csvFile : csvFiles) {
//            List<String[]> csvData = readCsvFile(csvFolderPath + "/" + csvFile);
//            List<Map<String, Object>> jsonData = convertToJSON(csvData);
//            jsonDataList.add(jsonData);
//        }
//
//        return jsonDataList;
//    }
//
//    private static List<Map<String, Object>> convertToJSON(List<String[]> csvData) {
//        List<Map<String, Object>> jsonData = new ArrayList<>();
//        String[] headers = csvData.get(0);
//
//        for (int i = 1; i < csvData.size(); i++) {
//            String[] values = csvData.get(i);
//            Map<String, Object> data = new HashMap<>();
//
//            for (int j = 0; j < headers.length; j++) {
//                String header = headers[j];
//                String value = values[j];
//
//                // Classify data type
//                Object typedValue;
//                if (isNumeric(value)) {
//                    typedValue = parseNumericValue(value);
//                } else if (isDate(value)) {
//                    typedValue = formatDateValue(value);
//                } else {
//                    typedValue = value;
//                }
//
//                data.put(header, typedValue);
//            }
//
//            jsonData.add(data);
//        }
//
//        return jsonData;
//    }
//
//    private static List<String> listCsvFiles(String csvFolderPath) {
//        List<String> csvFiles = new ArrayList<>();
//        try {
//            java.io.File folder = new java.io.File(csvFolderPath);
//            java.io.File[] files = folder.listFiles();
//            for (java.io.File file : files) {
//                if (file.isFile() && file.getName().endsWith(".csv")) {
//                    csvFiles.add(file.getName());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return csvFiles;
//    }
//
//    private static List<String[]> readCsvFile(String csvFilePath) {
//        List<String[]> csvData = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                csvData.add(data);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return csvData;
//    }
//
//    private static boolean isNumeric(String value) {
//        try {
//            Double.parseDouble(value);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    private static boolean isDate(String value) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        dateFormat.setLenient(false);
//
//        try {
//            dateFormat.parse(value);
//            return true;
//        } catch (ParseException e) {
//            return false;
//        }
//    }
//
//    private static Object parseNumericValue(String value) {
//        if (value.contains(".")) {
//            return Double.parseDouble(value);
//        } else {
//            return Long.parseLong(value);
//        }
//    }
//
//    private static String formatDateValue(String value) {
//        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date = inputDateFormat.parse(value);
//            return "\"" + outputDateFormat.format(date) + "\"";
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public static String generateJsonData(List<List<Map<String, Object>>> jsonDataList) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        String separator = "";
//
//        for (List<Map<String, Object>> jsonData : jsonDataList) {
//            for (Map<String, Object> data : jsonData) {
//                Map<String, Object> modifiedData = new HashMap<>();
//                for (Map.Entry<String, Object> entry : data.entrySet()) {
//                    String key = entry.getKey();
//                    Object value = entry.getValue();
//
//                    String type = getType(value); // Mendapatkan tipe data dari nilai
//
//                    // Menggabungkan tipe data dengan nilai dalam bentuk objek JSON
//                    Map<String, Object> jsonField = new LinkedHashMap<>();
//                    jsonField.put("value", removeQuotes(value));
//                    jsonField.put("type", type);
//
//                    modifiedData.put(key, jsonField);
//                }
//
//                String jsonStr = gson.toJson(modifiedData);
//                sb.append(separator).append(jsonStr);
//                separator = ", ";
//            }
//        }
//
//        sb.append("]");
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
//
//    private static String removeQuotes(Object value) {
//        if (value instanceof String) {
//            String stringValue = (String) value;
//            if (stringValue.startsWith("\"") && stringValue.endsWith("\"")) {
//                return stringValue.substring(1, stringValue.length() - 1);
//            }
//        }
//        return value.toString();
//    }
//
//
//    private static String getType(Object value) {
//        if (value instanceof String) {
//            return "string";
//        } else if (value instanceof Number) {
//            return "number";
//        } else if (value instanceof Date) {
//            return "date";
//        } else {
//            return "unknown";
//        }
//    }
//
//}