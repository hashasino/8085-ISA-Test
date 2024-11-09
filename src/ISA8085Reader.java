import java.io.BufferedReader; // Importing non-default libraries
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ISA8085Reader { // Creating reader class

	// To provide options to display the stored data in multiple formats
	public enum OutputFormat {
		TABLE,      // Traditional table format
		DETAILED    // Detailed multi-line format
	}

	// List to store the data
	private static final List<String[]> CsvData = new ArrayList<>();

	static int[] answeredTYPE = new int[5];
	static int[] typeRight = new int[5];

	//The main function with example implementation
	public static void main(String[] args) {

		// Creating an instance of ISA8085Reader and loading the CSV data
		loadCsvData("assets/8085 ISA.csv");

		// Setting default output format
		OutputFormat format = OutputFormat.TABLE;

		// Parsing format argument if provided
		if (args.length > 0) {
			try {
				format = OutputFormat.valueOf(args[0].toUpperCase());
			} catch (IllegalArgumentException e) {
				System.err.println("Invalid format. Using default table format.");
			}
		}

		printData(format); // Printing the loaded data based on the chosen format
	}

	// Method to load CSV data and store it in the program
	public static void loadCsvData(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			// Read header and store it as the first entry in csvData
			String[] headers = parseCsvLine(br.readLine());
			CsvData.add(headers);

			// Skip empty line
			br.readLine();

			// Read and store data rows
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					String[] data = parseCsvLine(line);
					if (data.length >= 5) {
						CsvData.add(data);
					}
				}
			}

		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
	}

	// CSV line parsing method to handle quoted fields with commas
	private static String[] parseCsvLine(String line) {
		List<String> tokens = new ArrayList<>();
		StringBuilder currentToken = new StringBuilder();
		boolean inQuotes = false;

		for (char ch : line.toCharArray()) {
			if (ch == '"') {
				// Toggle the inQuotes flag
				inQuotes = !inQuotes;
			} else if (ch == ',' && !inQuotes) {
				// If not inside quotes, treat this comma as a delimiter
				tokens.add(currentToken.toString().trim());
				currentToken.setLength(0);  // Clear the current token
			} else {
				// Otherwise, just add the character to the current token
				currentToken.append(ch);
			}
		}

		// Add the last token
		tokens.add(currentToken.toString().trim());

		return tokens.toArray(new String[0]);
	}


	// To get total count for each TYPE of data i.e. CI/LI/AI/BI/DTI
	public static int[] totalTYPECounter() {

		int[] typeCount = new int[5];

		loadCsvData("assets/8085 ISA.csv");

		for (String[] csvDatum : CsvData) {

			switch (csvDatum[0]) {
				case "CI":
					typeCount[0]++;
					break;
				case "LI":
					typeCount[1]++;
					break;
				case "DI":
					typeCount[2]++;
					break;
				case "BI":
					typeCount[3]++;
					break;
				case "DTI":
					typeCount[4]++;
					break;
				default:
					break;
			}
		}

		return typeCount;
	}

	public static void tagTYPEAnswered(String type) {
		switch (type) {
			case "CI":
				answeredTYPE[0]++;
				break;
			case "LI":
				answeredTYPE[1]++;
				break;
			case "DI":
				answeredTYPE[2]++;
				break;
			case "BI":
				answeredTYPE[3]++;
				break;
			case "DTI":
				answeredTYPE[4]++;
				break;
			default:
				break;
		}
	}

	public static void tagTYPERight(String type) {
		switch (type) {
			case "CI":
				typeRight[0]++;
				break;
			case "LI":
				typeRight[1]++;
				break;
			case "DI":
				typeRight[2]++;
				break;
			case "BI":
				typeRight[3]++;
				break;
			case "DTI":
				typeRight[4]++;
				break;
			default:
				break;
		}
	}


	// Public getter to access CSV data from other classes
	public static List<String[]> getCsvData() {
		return compactRows(CsvData);
	}

	public static List<String[]> compactRows(List<String[]> data) {
		if (data == null || data.isEmpty()) {
			return new ArrayList<>();
		}

		List<String[]> result = new ArrayList<>();

		// Add only non-empty rows to the result list
		for (String[] row : data) {
			if (!isEmptyRow(row)) {
				result.add(row.clone()); // Clone to avoid modifying original data
			}
		}

		return result;
	}

	private static boolean isEmptyRow(String[] row) {
		if (row == null) {
			return true;
		}

		for (String cell : row) {
			if (cell != null && !cell.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}


	// Method to print data based on the output format
	public static void printData(OutputFormat format) {
		// Print header
		printHeader(CsvData.getFirst(), format);

		// Print each row of data
		for (int i = 1; i < CsvData.size(); i++) {
			printRow(CsvData.get(i), format);
		}
	}

	// Method to print the header based on the output format
	private static void printHeader(String[] headers, OutputFormat format) {
		switch (format) {
			case TABLE:
				System.out.println("\n=== 8085 Instruction Set Architecture ===\n");
				System.out.printf("%-4s %-6s %-12s %-55s %s%n", headers[0], headers[1], headers[2], headers[3], headers[4]);
				System.out.println("=".repeat(150));
				break;

			case DETAILED:
				System.out.println("=== 8085 Instruction Set Architecture ===");
				System.out.println("----------------------------------------");
				break;
		}
	}

	// Method to print each row of data based on the output format
	private static void printRow(String[] data, OutputFormat format) {
		switch (format) {
			case TABLE:
				System.out.printf("%-4s %-6s %-12s %-55s %s%n", data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
				break;

			case DETAILED:
//				System.out.println("\nInstruction Details:");
				System.out.println("Type: " + data[0].trim());
				System.out.println("Opcode: " + data[1].trim());
				System.out.println("Operand: " + data[2].trim());
				System.out.println("Meaning: " + data[3].trim());
				System.out.println("Explanation: " + data[4].trim());
				System.out.println("----------------------------------------");
				break;
		}
	}

}
