package com.csvreader;


import com.csvreader.mapper.FilenameReader;
import com.csvreader.mapper.DBCreateTable;
import com.csvreader.restapiclient.RestApiClient;
import com.csvreader.converter.CsvReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RestApiClientApp {

	public static void main(String[] args) throws IOException {
		DBCreateTable DBCreateTable = new DBCreateTable();
		DBCreateTable.CreateTable();
		boolean exit = false;

		while (!exit) {
//			System.out.println("Waktu terbaru akses database:");
			//total waktu
			menu();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Input : ");
			int option = scanner.nextInt();

			RestApiClient rest = new RestApiClient();
			FilenameReader filenameReader = new FilenameReader();
			String path = "src/main/resources/Mapping";
			List<String> fileNames = filenameReader.fileNames(path);

			switch (option) {
				case 1:
					int posTotTime = 0;
					int posTotRow = 0;
					int casTotTime = 0;
					int casTotRow = 0;

					for (String filename : fileNames) {
						String filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
						Map<String, Object> postgresResult = rest.Get("http://localhost:8080/api/postgres", filenameWithoutExtension);
						Map<String, Object> cassandraResult = rest.Get("http://localhost:8081/api/cassandra", filenameWithoutExtension);

						List<Map<String,Object>> postgresData = (List<Map<String, Object>>) postgresResult.get("data");
						String timeResponsePos = (String) postgresResult.get("time");
						List<Map<String,Object>> cassandraData = (List<Map<String, Object>>) cassandraResult.get("data");
						String timeResponseCas = (String) cassandraResult.get("time");

						System.out.println("POSTGRES");
						System.out.println(filenameWithoutExtension + " Data :");
						System.out.println(postgresData);
						System.out.println("Time : " + timeResponsePos + " ms");
						System.out.println("Row Total : " + postgresData.size());
						System.out.println("\n");
						posTotTime = Integer.parseInt(posTotTime + timeResponsePos);
						posTotRow = posTotRow + postgresData.size();

						System.out.println("CASSANDRA");
						System.out.println(filenameWithoutExtension + " Data :");
						System.out.println(cassandraData);
						System.out.println("Time : " + timeResponseCas + " ms");
						System.out.println("Row Total : " + cassandraData.size());
						System.out.println("\n");
						casTotTime = Integer.parseInt(casTotTime + timeResponseCas);
						casTotRow = casTotRow + cassandraData.size();
					}

					System.out.println("== Conclusion ==");
					System.out.println("Postgres : ");
					System.out.println("Total Row \t\t: " + posTotRow);
					System.out.println("Total Response Time \t: " + posTotTime + " ms");
					System.out.println("Cassandra : ");
					System.out.println("Total Row \t\t: " + casTotRow);
					System.out.println("Total Response Time \t: " + casTotTime + " ms");
					System.out.println("\n\n");
					break;
				case 2:
					System.out.print("Masukkan path folder yang ingin diinput: ");
					scanner.nextLine();
					String path_folder = scanner.nextLine();
					CsvReader.Post(path_folder);
					break;

				case 3:
					for (String filename : fileNames) {
						String filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
						rest.TruncateTable("http://localhost:8080/api/postgres/truncate", filenameWithoutExtension);
						rest.TruncateTable("http://localhost:8081/api/cassandra/truncate", filenameWithoutExtension);
					}
					break;
				case 4:
					exit = true;
					break;
				default:
					System.out.println("\n\n");
					System.out.println("Option is not avalable");
					System.out.println("\n\n");
			}
		}
	}

	private static void menu() {
		System.out.print("""
				Main Menu:
				1. Get All Data
				2. Post All Data 
				3. Delete Data
				4. Exit
				\s""");
	}

}
