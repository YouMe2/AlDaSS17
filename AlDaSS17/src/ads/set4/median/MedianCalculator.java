/**
 * 
 */
package ads.set4.median;

import java.util.Arrays;

/**
 * @author Yannik Eikmeier
 * @since 12.06.2017
 *
 */
public class MedianCalculator {

	//private test
	public static void main(String[] args) {
		int[] nums = new int[] { 8, 4, 9, 7, 6, 5, 3, 1, 2 };
		System.out.println("got:    " + median(nums, 3));
		Arrays.sort(nums);
		System.out.println("wanted: " + nums[(nums.length - 1) / 2]);
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	*/
	
	/**
	 * Finds the median in the given array of numbers. The algorithm should use
	 * the algorithm presented in the lecture and accept different group sizes
	 * for this.
	 * 
	 * @param numbers
	 *            the unsorted array of number. Must not contain duplicates.
	 * @param groupSize
	 *            the size of the groups the array should be split into. Will
	 *            always be an odd number between 3 and 9.
	 * @return the median of the numbers
	 */
	public static int median(int[] numbers, int groupSize) {
		if (groupSize != 3 && groupSize != 5 && groupSize != 7 && groupSize != 9)
			throw new IllegalArgumentException("Unallowed groupsize of: " + groupSize);
		return elementAt(numbers, (numbers.length - 1) / 2, groupSize);
	}

	/**
	 * Finds and returns the number that would be at a given index in a sorted
	 * version of the numbers.
	 * 
	 * @param numbers
	 *            the unsorted array of number. Must not contain duplicates.
	 * @param index the index
	 * @param groupSize
	 *            the size of the groups the array should be split into. Will
	 *            always be an odd number between 3 and 9.
	 * @return the number that would be at the given index in a sorted version
	 *         of the numbers
	 */
	private static int elementAt(int[] numbers, int index, int groupSize) {
		if (groupSize != 3 && groupSize != 5 && groupSize != 7 && groupSize != 9)
			throw new IllegalArgumentException("Unallowed groupsize of: " + groupSize);
		if (numbers.length <= groupSize) {
			// abbruch der rekursion bei begrentzer grˆﬂe -> kein einfluﬂ auf
			// laufzeit im sinne der O-notation.
			return naiveElementAt(numbers, index);
		}

		int sublists = numbers.length / groupSize; // number of full sublists
		int[] subNumbers;
		int[] medians = new int[sublists + ((numbers.length % groupSize != 0) ? 1 : 0)];

		for (int i = 0; i < sublists; i++) {
			subNumbers = Arrays.copyOfRange(numbers, i * groupSize, (i + 1) * groupSize);
			Arrays.sort(subNumbers);
			medians[i] = subNumbers[groupSize / 2]; // median aller sublists
		}

		if (medians.length > sublists) {
			// last one!
			subNumbers = Arrays.copyOfRange(numbers, sublists * groupSize, numbers.length);
			Arrays.sort(subNumbers);
			medians[sublists] = subNumbers[(numbers.length % groupSize - 1) / 2]; // median
																					// aller
																					// sublists
		}

		int medOMed = median(medians, groupSize);

		int[][] L = partition(numbers, medOMed);
		assert L[0].length + L[1].length + 1 == numbers.length;

		int k = L[0].length;

		if (index == k) {
			return medOMed;
		} else {
			if (index < k) {
				return elementAt(L[0], index, groupSize);
			} else {
				assert index > k;
				return elementAt(L[1], index - (k + 1), groupSize);
			}
		}
	}

	/**
	 * performs a partition of the given numbers into all the ones bigger and
	 * the ones smaller than x.
	 * 
	 * @param numbers
	 * @param x
	 * @return two int[]s: [0] with <x and [1] with >x values
	 */
	private static int[][] partition(int[] numbers, int x) {

		int[][] L = new int[2][];

		int i = 0;
		int j = numbers.length - 1;
		while (i != j) {
			while (i != j && numbers[i] < x) {
				i++;
			}
			while (i != j && numbers[j] > x) {
				j--;
			}
			if (i < j) {
				int temp = numbers[i];
				numbers[i] = numbers[j];
				numbers[j] = temp;
			}
		}
		assert numbers[i] == x;
		L[0] = Arrays.copyOfRange(numbers, 0, i);
		L[1] = Arrays.copyOfRange(numbers, i + 1, numbers.length);
		return L;
	}

	/**
	 * Performs a naive {@code Arrays.sort()} and returns the value at the given
	 * index.
	 * 
	 * @param numbers
	 * @param index
	 * @return the value at index in a sorted version of numbers
	 */
	private static int naiveElementAt(int[] numbers, int index) {
		int[] num = Arrays.copyOf(numbers, numbers.length);
		Arrays.sort(num);
		return num[index];

	}

}
