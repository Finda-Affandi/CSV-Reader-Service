package com.csvreader;

import java.io.IOException;
import java.util.Scanner;

public class RestApiClientApp {

	public static void main(String[] args) throws IOException {
		boolean exit = false;

		while (!exit) {
			menu();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Input : ");
			int option = scanner.nextInt();

			switch (option) {
				case 1:
					System.out.println("1");
					break;
				case 2:
					System.out.println("2");
					break;
				case 3:
					System.out.println("3");
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