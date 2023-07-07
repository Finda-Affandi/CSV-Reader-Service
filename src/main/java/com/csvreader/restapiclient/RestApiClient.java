package com.csvreader.restapiclient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;


public class RestApiClient {

    private String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }
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
                    System.out.print("Success! Database = Cassandra\t\t");
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

    public String GetAll(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "Application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("Failed to get data. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String GetByTable(String apiUrl, String param) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "Application/json");
            conn.setRequestProperty("param", param);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("Failed to get data. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<String> ChooseTable(String apiUrl) {
        List<String> data = new ArrayList<>();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(inputLine);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String output = jsonArray.getString(i);
                            data.add(output); // Menambahkan data ke dalam list
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                in.close();
            } else {
                System.out.println("Failed to get data. Response Code: " + responseCode);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data; // Mengembalikan list data
    }



}
