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
					String result1 = getAllPostgres.GetAll("http://localhost:8080/api/postgres");
					// Lakukan sesuatu dengan hasil yang diterima, misalnya:
					System.out.println(result1);
					break;
				case 2:
                                        CSVToJsonConverter objek = new CSVToJsonConverter();
					objek.Post();
//					RestApiClient chooseTable = new RestApiClient();
//					String result2 = chooseTable.ChooseTable("http://localhost:8080/api/chooseTablePosgres").toString();
//					System.out.println(result2);
//
//					Scanner choose  = new Scanner(System.in);
//					System.out.print("Pilih Table : ");
//					int pilihan = choose.nextInt();
//					RestApiClient getByTablePostgres = new RestApiClient();
//					String result2a;
//					String apiUrlByTable = "http://localhost:8080/api/getByTablePostgres";
//					switch (pilihan) {
//						case 1:
//							result2a = getByTablePostgres.GetByTable(apiUrlByTable, "1");
//							System.out.println(result2a);
//							break;
//						case 2:
//							result2a = getByTablePostgres.GetByTable(apiUrlByTable, "2");
//							System.out.println(result2a);
//							break;
//
//					}

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
				3. Exit
				\s""");


	}

}