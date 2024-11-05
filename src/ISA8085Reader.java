import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ISA8085Reader {
	// Format enumeration with only TABLE and DETAILED options
	public enum OutputFormat {
		TABLE,      // Traditional table format
		DETAILED    // Detailed multi-line format
	}

	// List to store the data
	private final List<String[]> csvData = new ArrayList<>();

	public static void main(String[] args) {
		// Hardcoded file path
		String filePath = "assets/8085 ISA.csv";
		OutputFormat format = OutputFormat.TABLE;  // Default format

		// Parse format argument if provided
		if (args.length > 0) {
			try {
				format = OutputFormat.valueOf(args[0].toUpperCase());
			} catch (IllegalArgumentException e) {
				System.err.println("Invalid format. Using default table format.");
			}
		}

		// Create an instance of ISA8085Reader and load the CSV data
		ISA8085Reader reader = new ISA8085Reader();
		reader.loadCsvData(filePath);

		// Print the loaded data based on the chosen format
		reader.printData(format);
	}

	// Method to load CSV data and store it in the program
	public void loadCsvData(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// Read header and store it as the first entry in csvData
			String[] headers = parseCsvLine(br.readLine());
			csvData.add(headers);

			// Skip empty line
			br.readLine();

			// Read and store data rows
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					String[] data = parseCsvLine(line);
					if (data.length >= 5) {
						csvData.add(data);
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

	// Public getter to access CSV data from other classes
	public List<String[]> getCsvData() {
		return compactRows(csvData);
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
	public void printData(OutputFormat format) {
		// Print header
		printHeader(csvData.getFirst(), format);

		// Print each row of data
		for (int i = 1; i < csvData.size(); i++) {
			printRow(csvData.get(i), format);
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
