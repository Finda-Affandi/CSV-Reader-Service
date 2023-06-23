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
