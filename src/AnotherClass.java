import java.util.List;

public class AnotherClass {
	public static void main(String[] args) {
		// Create an instance of ISA8085Reader
		ISA8085Reader reader = new ISA8085Reader();

		// Load the CSV data
		reader.loadCsvData("assets/8085 ISA.csv");

		// Get the CSV data
		List<String[]> csvData = reader.getCsvData();

		// Print the first row (header)
		String[] headers = csvData.get(0);
		System.out.println("Headers: " + String.join(", ", headers));

		// Print the first data row
		String[] firstRow = csvData.get(1);
		System.out.println("First Row: " + String.join(", ", firstRow));

		// Print the second data row
		String[] secondRow = csvData.get(2);
		System.out.println("Second Row: " + String.join(", ", secondRow));
	}
}
