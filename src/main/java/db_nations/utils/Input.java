package db_nations.utils;

import java.util.Scanner;

public class Input {
	public static int getInt(Scanner scanner, int min, int max, String requestMessage) {

		String inputValue;
		int x = 0;
		boolean inputSuccess = false;

		System.out.println(requestMessage);

		while (!inputSuccess) {
			inputValue = scanner.nextLine();
			try {
				x = Integer.parseInt(inputValue);

				if (x < min)
					if (min != max)
						System.err.println("Errore! Devi inserire un numero maggiore o uguale a " + min + ". Riprova");
					else
						System.err.println("Errore! Puoi inserire solo " + min + ". Riprova");
				else if (x > max)
					if (min != max)
						System.err.println("Errore! Devi inserire un numero minore o uguale a " + max + ". Riprova");
					else
						System.err.println("Errore! Puoi inserire solo " + min + ". Riprova");
				else
					inputSuccess = true;
			} catch (NumberFormatException e) {
				System.err.println("Errore! Devi inserire un numero intero valido. Riprova");
			}
		}

		Output.printSeparator();

		return x;
	}

	public static String getString(Scanner scanner, String requestMessage) {
		String s = "";
		boolean inputSuccess = false;

		System.out.println(requestMessage);

		while (!inputSuccess) {

			s = scanner.nextLine();

			if (s.isBlank())
				System.err.println("Errore! Devi inserire una stringa valida. Riprova");
			else
				inputSuccess = true;

		}

		Output.printSeparator();

		return s.strip();
	}
}
