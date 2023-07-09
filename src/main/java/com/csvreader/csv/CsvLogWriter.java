package com.csvreader.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvLogWriter {
    public void writer(String[] data) {
        String CSV_FILE_PATH = "D:\\Documents\\log.csv";

        try {
            FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Append data to the CSV file
            for (String value : data) {
                bufferedWriter.write(value);
                bufferedWriter.write(",");
            }

            // Add a new line
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }
}
