package com.csvreader;

import com.csvreader.mapper.PostMapping;
import com.csvreader.restapiclient.RestApiClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class tryerror {
    public static void main(String[] args) throws IOException {
//        PostMapping postMapping = new PostMapping();
//        postMapping.CreateTable();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> teacher = new HashMap<>();
        Map<String, Object> student = new HashMap<>();

        teacher.put("name", "fandi");
        teacher.put("age", "11");
        teacher.put("city", "Salatiga");

        student.put("name", "Budi");
        student.put("School", "UKSW");
        student.put("city", "Salatiga");

        dataMap.put("teacher", teacher);
        dataMap.put("student", student);

        String postgreSQLUrl = "http://localhost:8080/api/postgrescoba";
        RestApiClient restApiClient = new RestApiClient();
        restApiClient.Post(postgreSQLUrl, dataMap.toString(), "yes");
    }
}
