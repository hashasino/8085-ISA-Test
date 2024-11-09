import java.util.HashSet; // Importing non-default libraries
import java.util.List;
import java.util.Random;

public class UniqueRandomIntegers { // Creating the randomizer class

	// Method to generate an array of random but unique integers
	public static int[] getUniqueIntegers() {

		ISA8085Reader.loadCsvData("H:/My Drive/Programming/Java/IdeaProjects/8085 ISA Test/assets/8085 ISA.csv");
		List<String[]> csvData = ISA8085Reader.getCsvData();

		int arraySize = csvData.size() - 1; // Size of the array
		int minValue = 1;   // Minimum value (inclusive)
		int maxValue = arraySize; // Maximum value (inclusive)

		int[] uniqueIntegers = new int[arraySize]; // Initializing integer array
		HashSet<Integer> uniqueSet = new HashSet<>(); // Initializing an Integer HashSet
		Random random = new Random(); // Generating a random number between 0 & 1

		int index = 0;

		// Assigning unique & random values to the integer array
		while (index < arraySize) {
			int randomValue = random.nextInt(maxValue - minValue + 1) + minValue;

			// Check for uniqueness
			if (uniqueSet.add(randomValue)) {
				uniqueIntegers[index] = randomValue;
				index++;
			}
		}

		return uniqueIntegers; // Returning the integer array as result
	}
}
