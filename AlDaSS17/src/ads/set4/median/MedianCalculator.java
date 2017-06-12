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
	
	public static void main(String[] args) {
		System.out.println(median(new int[]{9,5,1,7,8,2,4,3,6,14,84,123,789,124,456}, 3));
										//	1 2 3 4 5 6 7 8 9 14 84 123 124 456 789
										//	- - - - - - - x - -  -  -   -   -   -
	}
	
	
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
			throw new IllegalArgumentException("Unallowed groupsize of: "+groupSize);
		return elementAt(numbers, (numbers.length-1)/2 ,groupSize);
	}

	/**
	 * @param numbers
	 * @param index
	 * @param groupSize
	 * @return the number that would be at the given index in a sorted version of the numbers
	 */
	private static int elementAt(int[] numbers, int index, int groupSize) {
		if (numbers.length <= groupSize) {
			return naiveMedian(numbers);
		}
		
		
		int sublists = numbers.length/groupSize; //number of full sublists 
		int[] subNumbers;
		int[] medians = new int[sublists + ((numbers.length%groupSize != 0) ? 1 : 0)];
		
		for (int i = 0; i < sublists; i++) {		
				subNumbers = Arrays.copyOfRange(numbers, i*groupSize, (i+1)*groupSize);
				Arrays.sort(subNumbers);
				medians[i] = subNumbers[groupSize/2];	//median aller sublists			
		}
		
		if ( medians.length > sublists) {
			// last one!
			subNumbers = Arrays.copyOfRange(numbers, sublists*groupSize, numbers.length);
			Arrays.sort(subNumbers);
			medians[sublists] = subNumbers[(numbers.length%groupSize)/2];	//median aller sublists				
		}
		
		int medOMed = median(medians, groupSize);
		
		int[][] L = partition(numbers, medOMed);
		
		int k = L[0].length;
		
		if (index == k){
			return medOMed;
		}
		else {
			if (index < k) {
				return elementAt(L[0], index, groupSize);
			}
			else {
				assert index > k;
				return elementAt(L[1], index - L[0].length+1, groupSize);
			}
		}
	}
	
	

	/**
	 * @param numbers
	 * @param medOMed
	 * @return
	 */
	private static int[][] partition(int[] numbers, int med) {
		
		int[][] L = new int[2][];

		int i = 0;
		int j = numbers.length-1;
		while ( i != j ){			
			while (i != j && numbers[i] < med){
				i++;
			}
			while (i != j && numbers[j] > med){
				j--;
			}
			if (i < j){
				int temp = numbers[i];
				numbers[i] = numbers[j];
				numbers[j] = temp;
			}		
		}
		assert numbers[i] == med;
		L[0] = Arrays.copyOfRange(numbers, 0, i);
		L[1] = Arrays.copyOfRange(numbers, i+1, numbers.length);	
		return L;
	}

	/**
	 * @param numbers
	 * @return
	 */
	private static int naiveMedian(int[] numbers) {
		if (numbers.length > 2) {
			int[] num = Arrays.copyOf(numbers, numbers.length);
			Arrays.sort(num);
			return num[num.length/2];			
		}
		else if (numbers.length == 2){
			return Math.min(numbers[0], numbers[1]);
		}
		else if (numbers.length == 1){
			return numbers[0];
		}
		else if (numbers.length == 0){
			throw new IllegalArgumentException("no median in empty array!");
		}
		return 0;
	}
}
