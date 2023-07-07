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
			System.out.println("Waktu terbaru akses database:");
			//total waktu
			menu();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Input : ");
			int option = scanner.nextInt();

			switch (option) {
				case 1:
					RestApiClient getAllPostgres = new RestApiClient();
					RestApiClient getAllCassandra = new RestApiClient();
					String result1 = getAllPostgres.GetAll("http://localhost:8080/api/postgres");

					String result2 = getAllCassandra.GetAll("http://localhost:8081/api/cassandra");
					System.out.println(result1);
					System.out.println(result2);
					break;
				case 2:
					CSVToJsonConverter objek = new CSVToJsonConverter();
					objek.Post();
					break;
				case 3:
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
