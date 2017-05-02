/**
 * 
 */
package ads.set1.search;

/**
 * @author Yannik Eikmeier
 * @since 02.05.2017
 *
 */
public class Search {

	/**
	 * Looks for the given element (the "needle") in the given array (the "hay")
	 * using a linear search algorithm.
	 * 
	 * @param hay
	 *            array to search for the needle, sorted in ascending order.
	 * @param needle
	 *            the value to search for in the array.
	 * @return index of the needle in the array, if it could be found, or
	 *         {@code -1} otherwise.
	 */
	public static int linearSearch(int[] hay, int needle) {

		for (int i = 0; i < hay.length; i++) {
			if (hay[i] == needle)
				return i;
		}
		return -1;
	}

	/**
	 * Looks for the given element (the "needle") in the given array (the "hay")
	 * using a binary search algorithm.
	 * 
	 * @param hay
	 *            array to search for the needle, sorted in ascending order.
	 * @param needle
	 *            the value to search for in the array.
	 * @return index of the needle in the array, if it could be found, or
	 *         {@code -1} otherwise.
	 */
	public static int binarySearch(int[] hay, int needle) {

		
		int i = 0;
		int j = hay.length - 1;
		int mid;

		while (i <= j) {
			mid = (i + j) / 2;

			if (needle < hay[mid]) {
				
				j = mid - 1;
				
			} else if (needle > hay[mid]) {
				
				i = mid + 1;
				
			}
			else
				// now: needle == may[mid] is true!
				return mid;
		}

		// nothing found
		return -1;
	}
	
}
