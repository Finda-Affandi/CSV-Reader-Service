package com.csvreader;

import com.csvreader.mapper.PostMapping;
import com.csvreader.restapiclient.RestApiClient;
import com.csvreader.converter.CSVToJsonConverter;

import java.io.IOException;
import java.util.Scanner;

public class RestApiClientApp {

	public static void main(String[] args) throws IOException {
		PostMapping postMapping = new PostMapping();
		postMapping.CreateTable();
		boolean exit = false;

		while (!exit) {
			menu();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Input : ");
			int option = scanner.nextInt();

			switch (option) {
				case 1:
					RestApiClient getAllPostgres = new RestApiClient();
					String result1 = getAllPostgres.GetAll("http://localhost:8080/api/getAllDataPostgres");
					// Lakukan sesuatu dengan hasil yang diterima, misalnya:
					System.out.println(result1);
					break;
				case 2:
					RestApiClient chooseTable = new RestApiClient();
					String result2 = chooseTable.ChooseTable("http://localhost:8080/api/chooseTablePosgres").toString();
					System.out.println(result2);

					Scanner choose  = new Scanner(System.in);
					System.out.print("Pilih Table : ");
					int pilihan = choose.nextInt();
					RestApiClient getByTablePostgres = new RestApiClient();
					String result2a;
					String apiUrlByTable = "http://localhost:8080/api/getByTablePostgres";
					switch (pilihan) {
						case 1:
							result2a = getByTablePostgres.GetByTable(apiUrlByTable, "1");
							System.out.println(result2a);
							break;
						case 2:
							result2a = getByTablePostgres.GetByTable(apiUrlByTable, "2");
							System.out.println(result2a);
							break;

					}

					break;
				case 3:
					CSVToJsonConverter objek = new CSVToJsonConverter();
					objek.Post();
					break;
				case 4:
					System.out.println("4");
					break;
				case 5:
					System.out.println("no");
					break;
				case 6:
					System.out.println("6");
					break;
				case 7 :
					exit = true;
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
				1. Get All Data Postgres
				2. Get Data By Table Postgres
				3. Post Data Postgres
				4. Get All Data Cassandra
				5. Get Data By Table Cassandra
				6. Post Data Cassandra
				7. Exit
				\s""");


	}

}