package db_nations.utils;

public class Output {
	public static String CYAN = "\u001B[36m";
	public static String YELLOW = "\u001B[33m";
	public static String GREEN = "\u001B[32m";
	public static String RESET = "\u001B[0m";

	public static void printSeparator() {
		System.out.println("\n---------------------------------------------------\n");
	}

	public static void printGoodbye() {
		printGreen("Arrivederci!");
		printSeparator();
	}

	public static void printGreetings() {
		printSeparator();
		printGreen("Benvenuto!");
		printSeparator();
	}

	public static void printGreen(String message) {
		System.out.println(Output.GREEN + message + Output.RESET);
	}
}
