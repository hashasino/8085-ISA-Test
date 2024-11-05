import java.util.List;
import java.util.Scanner;

public class ISA8085Test {
	public static void main(String[] args) {

		Disclaimer();

		//Loading CSV data
		ISA8085Reader reader = new ISA8085Reader();
		reader.loadCsvData("assets/8085 ISA.csv");
		List<String[]> csvData = reader.getCsvData();

		int[] uniqueIntegers = UniqueRandomIntegers.getUniqueIntegers(); //Generating random order

		int counter = 1;
		int Score = 0;

		//Instantiating new scanner object
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		//Loop start
		while (!input.equalsIgnoreCase("STOP") && counter <= uniqueIntegers.length) {

			System.out.println("\n" + counter + ". The OPCODE is: " + csvData.get(uniqueIntegers[counter])[1]);

			System.out.print("\nEnter description: ");
			input = sc.nextLine();
			if (input.equalsIgnoreCase("stop")) break;

			System.out.println("\nEvaluate your answer using the following information:");
			System.out.println("    OPCODE: " + csvData.get(uniqueIntegers[counter])[1]);
			System.out.println("    TYPE: " + csvData.get(uniqueIntegers[counter])[0]);
			System.out.println("    OPERAND: " + csvData.get(uniqueIntegers[counter])[2]);
			System.out.println("    FUNCTION: " + csvData.get(uniqueIntegers[counter])[3]);
			System.out.println("    EXPLANATION: " + csvData.get(uniqueIntegers[counter])[4]);

			counter++;

			System.out.println("\nWas your description correct? YES/NO");
			do {
				input = sc.nextLine();
				if (input.equalsIgnoreCase("stop")) break;

				if (input.isEmpty()) {
					System.out.println("Invalid input. Try again.");
					continue;
				}

				switch (input.charAt(0)) {
					case 'y':
					case 'Y':
//						printMultiChar();
						System.out.println("\nGood job! Score: " + Score + " + 1 = " + (++Score));
						printMultiChar();
						break;
					case 'n':
					case 'N':
//						printMultiChar();
						System.out.println("\nNext time. Score: " + Score);
						printMultiChar();
						break;
					default:
						System.out.println("Invalid input. Try again.");
				}
			} while (input.isEmpty() || (input.charAt(0) != 'y' && input.charAt(0) != 'Y' && input.charAt(0) != 'n' && input.charAt(0) != 'N'));
		}

		EndScreen();
		System.out.println(counter);
	}

	static void printMultiChar() {
		System.out.println('\n' + "=".repeat(160));
	}

	static void Disclaimer() {
		System.out.println("\nThis program is to test you for recalling of 8085 Instructions.");
		System.out.println("\n- The OPCODE of the instruction will be shown to you, and you must recall it's function & other things.");
		System.out.println("- Input at what you can recall into the terminal, then press Enter to check if it's correct.");
		System.out.println("\n- If it is correct then select YES, else select NO. Once you are able to score 100% or close, you will have passed the test.");
		System.out.println("\n* As this is a self-graded test, whether you do well or not will depend on how you grade yourself, despite of how you've actually performed.");
		System.out.println("* So be honest with yourself as your only competition here is you yourself.");
		System.out.println("\n[CI - Control Instructions | LI - Logical Instructions | AI - Arithmetic Instructions | BI - Branching Instructions | DTI - Data Transfer Instructions]");
		System.out.println("\nWhen you are ready to proceed, enter anything. Enter STOP to stop the program & display results.\n");
	}

	static void EndScreen() {

		printMultiChar();

		System.out.println("\nTotal questions encountered: " + 'x' + " (z%)");
		System.out.print("\tCI - 1" + " | LI - 2" + " | AI - 3" + " | BI - 4" + " | DTI - 5");

		System.out.println("\nTotal questions right: " + 'y' + " (y%)");
		System.out.print("\tCI - 1" + " | LI - 2" + " | AI - 3" + " | BI - 4" + " | DTI - 5");

		System.out.println("\n" + 'z' + " more to go (x%)");
		System.out.println("\tCI - 1" + " | LI - 2" + " | AI - 3" + " | BI - 4" + " | DTI - 5");

		System.out.println("\n78% correct. 33% unencountered.");
//		System.out.println("You're almost there!");
//		System.out.println("Excellent! You have passed with flying colors!");
	}
}