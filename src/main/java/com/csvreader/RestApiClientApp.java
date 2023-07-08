package com.csvreader;


import com.csvreader.mapper.FilenameReader;
import com.csvreader.mapper.PostMapping;
import com.csvreader.restapiclient.RestApiClient;
import com.csvreader.converter.CSVToJsonConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RestApiClientApp {

	public static void main(String[] args) throws IOException {
		PostMapping postMapping = new PostMapping();
		postMapping.CreateTable();
		boolean exit = false;

		while (!exit) {
			System.out.println("Waktu terbaru akses database:");
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
					for (String filename : fileNames) {
						String filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
						Map<String, Object> postgresResult = rest.GetAll("http://localhost:8080/api/postgres", filenameWithoutExtension);
						Map<String, Object> cassandraResult = rest.GetAll("http://localhost:8081/api/cassandra", filenameWithoutExtension);

						List<Map<String,Object>> postgresData = (List<Map<String, Object>>) postgresResult.get("data");
//						Map<String, Object> cassandraData = (Map<String, Object>) cassandraResult.get("data");

						List <String> posColumn = new ArrayList<>();

						for (Map<String, Object> data : postgresData) {
							posColumn.addAll(data.keySet());
							break;
						}

						System.out.println(String.join("\t", posColumn));

						for (Map<String, Object> data : postgresData) {
							for (String col : posColumn) {
								System.out.print(data.get(col) + "\t");

							}
							System.out.print("\n");
						}
//						System.out.println(cassandraData);

//						System.out.println(postgresResult.get("data"));
//						System.out.println(cassandraResult.get("data"));


					}
					break;
				case 2:
					System.out.print("Masukkan path folder yang ingin diinput: ");
					scanner.nextLine();
					String path_folder = scanner.nextLine();
					CSVToJsonConverter objek = new CSVToJsonConverter();
					objek.Post(path_folder);
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
