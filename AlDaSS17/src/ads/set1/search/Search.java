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

		// Laufzeit allg: O(n), mit n = hey.length
		// Laufzeit hier da hay sorted ist genauer: O(needle)
		/*
		 * 
		 * LinearSearch benötigt im wesentlichen maximal die Zeit jedes Element
		 * des Arrays einzeln zuvergleichen. Diese maximale Zeit kann jedoch
		 * abhängig davon wie weit vorne das gesuchte Element im Array steht
		 * stark schwanken. So kann selbst bei einem riesigen Array das gesuchte
		 * Element inerhalb der ersten iterationen gefunden werden, aber es
		 * kann, zB falls das Element nicht einmal im Array enthalten ist, auch
		 * die volle maximale Zeit beanspucht werden.
		 * 
		 */

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

		// Laufzeit: O(ld(n)), mit n = hey.length

		/*
		 * BinarySearch reduziert in jedem Iterationsschritt den
		 * zudurchsuchenden Bereich im Array auf unter die Hälfte des vorherigen
		 * Bereiches, daher die sehr schnelle logarithmische Laufzeit, die
		 * nahezu unabhänging von der Arraylänge erscheinen mag.
		 * 
		 * Die feinheiten der Geschwindigkeits unterschiede bei den einzelnen
		 * Aufrufen von binarySearch sind außderm auch auch die position des
		 * gesuchten Elements zurückzuführen. So findet BinarySearch zB Elemente
		 * besonders schnell, wenn diese zufällig genau in der Mitte eines
		 * Suchbereichs liegen, da diese gewissermaßen als ersten überprüft
		 * werden.
		 * 
		 * Gleichzeitig ist die Zeit, die BinarySearch zum finden des gesuchten
		 * Elements benötigt, unabhänging davon wie weit vorne das gesuchte
		 * Element steht. So könnte es beispeils weise vorkommen, dass
		 * LinearSearch ein zufällig weit vorne stehendes Element schneller
		 * findet als BinarySearch. Dies sind jedoch zuvernachlässigende
		 * sonderfälle.
		 */

		int i = 0;
		int j = hay.length - 1;
		int mid;

		while (i <= j) {
			mid = (i + j) / 2;

			if (needle < hay[mid]) {

				j = mid - 1;

			} else if (needle > hay[mid]) {

				i = mid + 1;

			} else
				// now: needle == may[mid] is true!
				return mid;
		}

		// nothing found
		return -1;
	}

}
