package com.csvreader.restapiclient;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {
    public void Post(String apiUrl, String body) {
        try{
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "Application/json");

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Request was successful
                // Handle the API response here if needed
                System.out.println("JSON data posted successfully!");
            } else {
                // Request failed
                // Handle the error response here if needed
                System.out.println("Failed to post JSON data. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
