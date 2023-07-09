package com.csvreader.restapiclient;

import java.util.Map;

public class ResponseFormatter {
    public String Formatter(Map<String, Object> response){
        String table = response.get("table").toString();
        String duration = response.get("duration").toString();
        String row = response.get("row").toString();

        return "Table = " + table + "\t\t" +  "Total Row = " + row + "\t\t" + "Duration = " + duration + "ms";
    }
}
