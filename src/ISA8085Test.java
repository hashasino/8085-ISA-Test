import java.util.List; // Importing non-default libraries
import java.util.Scanner;

public class ISA8085Test { // Creating the test class

	static int counter = 0; // Initializing class's global variables
	static int Score = 0;
	final static int[] uniqueIntegers = UniqueRandomIntegers.getUniqueIntegers(); //Generating random order

	// The main function
	public static void main(String[] args) {

		Disclaimer(); // START

		//Loading CSV data
		ISA8085Reader.loadCsvData("assets/8085 ISA.csv");
		List<String[]> csvData = ISA8085Reader.getCsvData();

		//Instantiating new scanner object
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		//Loop start
		while (!input.equalsIgnoreCase("STOP") && counter <= uniqueIntegers.length - 1) {

			// Question
			System.out.println("\n" + (counter + 1) + ". The OPCODE is: " + csvData.get(uniqueIntegers[counter])[1]);

			// Asking for user's answer
			System.out.print("\nEnter description: ");
			input = sc.nextLine();
			if (input.equalsIgnoreCase("stop")) break;

			// Showing the correct answer for self-evaluation
			System.out.println("\nEvaluate your answer using the following information:");
			System.out.println("    OPCODE: " + csvData.get(uniqueIntegers[counter])[1]);
			System.out.println("    TYPE: " + csvData.get(uniqueIntegers[counter])[0]);
			System.out.println("    OPERAND: " + csvData.get(uniqueIntegers[counter])[2]);
			System.out.println("    FUNCTION: " + csvData.get(uniqueIntegers[counter])[3]);
			System.out.println("    EXPLANATION: " + csvData.get(uniqueIntegers[counter])[4]);

			// Incrementing stat for the TYPE of instruction
			ISA8085Reader.tagTYPEAnswered(csvData.get(uniqueIntegers[counter])[0]);

			counter++; // Incrementing question number

			System.out.println("\nWas your description correct? YES/NO");
			do {
				input = sc.nextLine(); //Taking input

				// Checking for special cases
				if (input.equalsIgnoreCase("stop")) break;
				if (input.isEmpty()) {
					System.out.println("Invalid input. Try again.");
					continue;
				}

				// Checking for YES/NO
				switch (input.charAt(0)) {
					case 'y':
					case 'Y':
						ISA8085Reader.tagTYPERight(csvData.get(uniqueIntegers[counter - 1])[0]);
						System.out.println("\nGood job! Score: " + Score + " + 1 = " + (++Score));
						printMultiChar();
						break;
					case 'n':
					case 'N':
						System.out.println("\nNext time. Score: " + Score);
						printMultiChar();
						break;
					default:
						System.out.println("Invalid input. Try again.");
				}
			} while (input.isEmpty() || (input.charAt(0) != 'y' && input.charAt(0) != 'Y'
					&& input.charAt(0) != 'n' && input.charAt(0) != 'N'));
		}

		EndScreen(uniqueIntegers.length, counter, Score); // END PROGRAM
	}

	// To print dividers ========
	static void printMultiChar() {
		System.out.println('\n' + "=".repeat(160));
	}

	// Start screen message / Program context
	static void Disclaimer() {
		System.out.println("\nThis program is to test you for recalling of 8085 Instructions.");
		System.out.println("\n- The OPCODE of the instruction will be shown to you, and you must recall it's function & other things.");
		System.out.println("- Input what you can recall into the terminal, then press Enter to check if it's correct.");
		System.out.println("\n- If it is correct then select YES, else select NO. Once you are able to score 100% or close, you will have passed the test.");
		System.out.println("\n* As this is a self-graded test, whether you do well or not will depend on how you grade yourself, despite of how you've actually performed.");
		System.out.println("* So be honest with yourself as your only competition here is you yourself.");
		System.out.println("\n[CI - Control Instructions | LI - Logical Instructions | AI - Arithmetic Instructions | BI - Branching Instructions | DTI - Data Transfer Instructions]");
		System.out.println("\nWhen you are ready to proceed, enter anything. Enter STOP to stop the program & display results.\n");
	}

	// To display scores & stats after end program
	static void EndScreen(int Total, int Answered, int Right) {

		printMultiChar(); //Divider

		// Doing Score Calculations
		int Remaining = Total - Answered;
		int percentAnswered = Math.round((float) Answered / Total * 100);
		int percentRight = 0, percentTotal = 0;
		try {
			percentRight = Math.round((float) Right / Answered * 100);
			percentTotal = percentAnswered * percentRight / 100;
		} catch (Exception _) {
		}

		// Doing Stats Calculations
		int[] tT = ISA8085Reader.totalTYPECounter(); // Total TYPE count
		int[] aT = ISA8085Reader.answeredTYPE; // Total TYPE answered
		int[] rT = ISA8085Reader.typeRight; // Total TYPE right

		// Printing out the Scores n Stats
		System.out.println("\n Questions answered: " + Answered + " out of " + Total + " total");
		System.out.print("\t CI - " + aT[0] + " | LI - " + aT[1] + " | AI - " + aT[2] + " | BI - " + aT[3] + " | DTI - " + aT[4]);

		System.out.println("\n\n Questions right: " + Right + " out of " + Answered + " answered");
		System.out.print("\t CI - " + rT[0] + " | LI - " + rT[1] + " | AI - " + rT[2] + " | BI - " + rT[3] + " | DTI - " + rT[4]);

		System.out.println("\n\n  In short, " + percentAnswered + "% answered of which " + percentRight + "% right (i.e. " + percentTotal + "% of total)");
		System.out.println("\n\t\t\t\t\t" + Remaining + " more to go_");

//		System.out.println("You're almost there!");
//		System.out.println("Excellent! You have passed with flying colors!");
	}
}