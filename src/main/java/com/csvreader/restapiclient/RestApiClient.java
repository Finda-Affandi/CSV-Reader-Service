package com.csvreader.restapiclient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class RestApiClient {

    public void Post(String apiUrl, String header, String body) {
        try{
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "Application/json");
            conn.setRequestProperty("table-name", header);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                if (apiUrl.contains("cassandra")) {
                    System.out.print("Success! Database = Cassandra\t");
                    InputStream inputStream = conn.getInputStream();
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> response = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
                    ResponseFormatter responseFormatter = new ResponseFormatter();
                    String result = responseFormatter.Formatter(response);
                    System.out.println(result + "\n");

                }else {
                    System.out.print("Success! Database = Postgre\t\t");
                    InputStream inputStream = conn.getInputStream();
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> response = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
                    ResponseFormatter responseFormatter = new ResponseFormatter();
                    String result = responseFormatter.Formatter(response);
                    System.out.println(result);
                }


            } else {
                System.out.println("Failed to post JSON data. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> GetAll(String apiUrl, String header) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "Application/json");
            conn.setRequestProperty("table-name", header);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();
                Gson gson = new Gson();
                TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>() {};
                Map<String, Object> resultMap = gson.fromJson(jsonResponse, typeToken.getType());
                return resultMap;
            } else {
                System.out.println("Failed to get data. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
