package com.csvreader.mapper;

import com.csvreader.restapiclient.RestApiClient;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class DBCreateTable {
    public void CreateTable() {
        MapReader mapReader = new MapReader();
        FilenameReader filenameReader = new FilenameReader();
        RestApiClient restApiClient = new RestApiClient();
        String endpointPostgres = "http://localhost:8080/api/postgres/create-table";
        String endpointCassandra = "http://localhost:8081/api/cassandra/create-table";

        String path = "src/main/resources/Mapping";
        List<String> fileNames = filenameReader.fileNames(path);

        for (String filename : fileNames) {
            Map<String, Object> mapping = mapReader.mapping(filename);
            Gson gson = new Gson();
            String json = gson.toJson(mapping);
            String filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
            restApiClient.CreateTable(endpointPostgres, filenameWithoutExtension, json.toString());
            restApiClient.CreateTable(endpointCassandra, filenameWithoutExtension, json.toString());
        }

    }
}
