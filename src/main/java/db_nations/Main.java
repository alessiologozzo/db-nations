package db_nations;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Scanner;

import db_nations.utils.Input;
import db_nations.utils.Output;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Server server = new Server("root", "", "3306", "db_nations");
		ResultSet resultSet = null;
		int choice = 1;

		Output.printGreetings();

		while (choice > 0) {
			choice = Input.getInt(scanner, 0, 3,
					"Scegli un opzione:\n1) Seleziona un paese per nome\n2) Seleziona un paese per id\n3) Stampa la lista dei paesi\n0) Esci");

			switch (choice) {
			case 1:
				String countryName = Input.getString(scanner, "Inserisci il nome del paese da selezionare");

				try {
					resultSet = server.executeParameterizedQuery("SELECT a.* FROM \r\n"
							+ "(SELECT c.name, c.country_id, c.area, r.name region, c2.name continent, cs.`year`, cs.population, cs.gdp\r\n"
							+ "FROM countries c\r\n" + "JOIN regions r ON r.region_id = c.region_id \r\n"
							+ "JOIN continents c2 ON c2.continent_id = r.continent_id \r\n"
							+ "JOIN country_stats cs ON c.country_id = cs.country_id\r\n"
							+ "JOIN country_languages cl ON c.country_id = cl.country_id \r\n"
							+ "JOIN languages l ON l.language_id = cl.language_id\r\n"
							+ "GROUP BY c.name, c.area, c.national_day, r.name, c2.name, cs.`year`, cs.population, cs.gdp) a\r\n"
							+ "JOIN (SELECT c.name, MAX(cs.`year`) `year`\r\n" + "FROM countries c\r\n"
							+ "JOIN country_stats cs ON c.country_id = cs.country_id\r\n"
							+ "GROUP BY c.name) b ON a.name=b.name AND a.`year`=b.`year`\r\n" + "WHERE a.name = ?",
							Arrays.asList(countryName));

					if (!resultSet.next())
						System.out.println("Non è stato trovato nessun paese con questo nome.");
					else {

						System.out.println("Dati aggiornati al " + resultSet.getString("year")
								+ "\n\nPaese selezionato: " + Output.CYAN + resultSet.getString("name") + Output.RESET
								+ "\nId: " + Output.CYAN + resultSet.getInt("country_id") + Output.RESET + "\nPil: "
								+ resultSet.getString("gdp") + "$\nPopolazione: " + resultSet.getString("population")
								+ "\nSuperficie: " + resultSet.getString("area") + " km quadrati\nRegione: "
								+ resultSet.getString("region") + "\nContinente: " + resultSet.getString("continent"));

						resultSet = server.executeParameterizedQuery(
								"SELECT c.name, l.`language` \r\n" + "FROM countries c\r\n"
										+ "JOIN country_languages cl ON c.country_id = cl.country_id\r\n"
										+ "JOIN languages l ON l.language_id = cl.language_id\r\n" + "WHERE name = ?",
								Arrays.asList(countryName));

						System.out.print("\nLingue: ");
						while (resultSet.next()) {
							System.out.print(resultSet.getString("language"));

							if (!resultSet.isLast())
								System.out.print(", ");
						}

						System.out.print("\n");
					}

					Output.printSeparator();

				} catch (Exception e) {
					System.err.println("MAIN: Errore durante l'esecuzione della query.");
				}
				break;

			case 2:
				int countryId = Input.getInt(scanner, 1, 1_000_000, "Inserisci l'id del paese da selezionare");

				try {
					resultSet = server.executeParameterizedQuery("SELECT a.* FROM \r\n"
							+ "(SELECT c.name, c.country_id, c.area, r.name region, c2.name continent, cs.`year`, cs.population, cs.gdp\r\n"
							+ "FROM countries c\r\n" + "JOIN regions r ON r.region_id = c.region_id \r\n"
							+ "JOIN continents c2 ON c2.continent_id = r.continent_id \r\n"
							+ "JOIN country_stats cs ON c.country_id = cs.country_id\r\n"
							+ "JOIN country_languages cl ON c.country_id = cl.country_id \r\n"
							+ "JOIN languages l ON l.language_id = cl.language_id\r\n"
							+ "GROUP BY c.name, c.area, c.national_day, r.name, c2.name, cs.`year`, cs.population, cs.gdp) a\r\n"
							+ "JOIN (SELECT c.name, MAX(cs.`year`) `year`\r\n" + "FROM countries c\r\n"
							+ "JOIN country_stats cs ON c.country_id = cs.country_id\r\n"
							+ "GROUP BY c.name) b ON a.name=b.name AND a.`year`=b.`year`\r\n"
							+ "WHERE a.country_id = ?", Arrays.asList("" + countryId));

					if (!resultSet.next())
						System.out.println("Non è stato trovato nessun paese con questo id.");
					else {

						System.out.println("Dati aggiornati al " + resultSet.getString("year")
								+ "\n\nPaese selezionato: " + Output.CYAN + resultSet.getString("name") + Output.RESET
								+ "\nId: " + Output.CYAN + resultSet.getInt("country_id") + Output.RESET + "\nPil: "
								+ resultSet.getString("gdp") + "$\nPopolazione: " + resultSet.getString("population")
								+ "\nSuperficie: " + resultSet.getString("area") + " km quadrati\nRegione: "
								+ resultSet.getString("region") + "\nContinente: " + resultSet.getString("continent"));

						resultSet = server.executeParameterizedQuery("SELECT c.country_id, l.`language` \r\n"
								+ "FROM countries c\r\n"
								+ "JOIN country_languages cl ON c.country_id = cl.country_id\r\n"
								+ "JOIN languages l ON l.language_id = cl.language_id\r\n" + "WHERE c.country_id = ?",
								Arrays.asList("" + countryId));

						System.out.print("\nLingue: ");
						while (resultSet.next()) {
							System.out.print(resultSet.getString("language"));

							if (!resultSet.isLast())
								System.out.print(", ");
						}

						System.out.print("\n");
					}

					Output.printSeparator();

				} catch (Exception e) {
					System.err.println("MAIN: Errore durante l'esecuzione della query.");
				}

				break;

			case 3:
				try {
					resultSet = server.executeQuery("SELECT c.name, c.country_id, r.name, c2.name \r\n"
							+ "FROM countries c\r\n" + "JOIN regions r ON r.region_id = c.region_id\r\n"
							+ "JOIN continents c2 ON c2.continent_id = r.continent_id\r\n" + "ORDER BY c.name");
					while (resultSet.next()) {
						System.out.println("Paese: " + resultSet.getString("c.name") + " || Id: "
								+ resultSet.getString("c.country_id") + " || Regione: " + resultSet.getString("r.name")
								+ " || Continente: " + resultSet.getString("c2.name"));
						Output.printSeparator();
					}
				} catch (Exception e) {
					System.err.println("Errore");
				}
				break;

			}
		}

		Output.printGoodbye();

		server.stop();
		scanner.close();
	}

}
