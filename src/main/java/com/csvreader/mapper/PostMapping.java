package com.csvreader.mapper;

import com.csvreader.restapiclient.RestApiClient;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class PostMapping {
    public void CreateTable() {
        MapReader mapReader = new MapReader();
        FilenameReader filenameReader = new FilenameReader();
        RestApiClient restApiClient = new RestApiClient();
        String endpoint = "http://localhost:8080/api/postgres/create-table";

        String path = "src/main/resources/Mapping";
        List<String> fileNames = filenameReader.fileNames(path);

        for (String filename : fileNames) {
            Map<String, Object> mapping = mapReader.mapping(filename);
            Gson gson = new Gson();
            String json = gson.toJson(mapping);
            String filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
            restApiClient.Post(endpoint, filenameWithoutExtension, json.toString());
        }

    }
}
