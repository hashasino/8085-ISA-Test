import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class UniqueRandomIntegers {
	public static int[] getUniqueIntegers () {

		ISA8085Reader reader = new ISA8085Reader();
		reader.loadCsvData("assets/8085 ISA.csv");
		List<String[]> csvData = reader.getCsvData();
//		System.out.println(csvData.size());

		int arraySize = csvData.size()-1; // Size of the array
		int minValue = 1;   // Minimum value (inclusive)
		int maxValue = arraySize; // Maximum value (inclusive)

		int[] uniqueIntegers = new int[arraySize];
		HashSet<Integer> uniqueSet = new HashSet<>();
		Random random = new Random();

		int index = 0;
		while (index < arraySize) {
			int randomValue = random.nextInt(maxValue - minValue + 1) + minValue;

			// Check for uniqueness
			if (uniqueSet.add(randomValue)) {
				uniqueIntegers[index] = randomValue;
				index++;
			}
		}

		return uniqueIntegers;

//		// Print the unique integers
//		for (int num : uniqueIntegers) {
//			System.out.print(num + " ");
//		}
	}
}
