/**
 * 
 */
package ads.set2.knapsack;

/**
 * @author Yannik Eikmeier
 * @since 17.05.2017
 *
 */
public class EfficientKnapsackSolver {

	public static void main(String[] args) {

		Item i0 = new Item(1, 1);
		Item i1 = new Item(2, 2);
		Item i2 = new Item(2, 3);
		Item i3 = new Item(3, 4);
		Item i4 = new Item(1, 4);
		Item i5 = new Item(2, 1);

		int r = pack(new Item[] { i0, i1, i2, i3, i4, i5 }, 6);
		System.out.println(r);
	}

	/**
	 * Calculates the maximum profit for the knapsack problem using an improved
	 * dynamic programming algorithm
	 * 
	 * @param items
	 *            the items available to be packed into the knapsack.
	 * @param capacity
	 *            the maximum weight allowed for the knapsack.
	 * @return the maximum profit possible for the given weight
	 */
	public static int pack(final Item[] items, final int capacity) {

		final int n = items.length;
		final int infinit = capacity + 1;
		int maxProfit = 0;
		for (Item i : items)
			maxProfit += i.getProfit();

		int[][] weight = new int[2][maxProfit + 1];

		for (int t = 1; t <= maxProfit; t++) {
			if (items[0].getProfit() != t)
				weight[0][t] = infinit;
			else
				weight[0][t] = items[0].getWeight();
		}

		for (int i = 1; i < n; i++) {
			for (int profit = 0; profit <= maxProfit; profit++) {

				Item item = items[i];
				int w = item.getWeight();
				int p = item.getProfit();

				if (profit < p || weight[(i - 1) % 2][profit] < weight[(i - 1) % 2][profit - p] + w)
					// keine verbesserung
					weight[i % 2][profit] = weight[(i - 1) % 2][profit];
				else
					// verbesserung durch neues item
					weight[i % 2][profit] = weight[(i - 1) % 2][profit - p] + w;

			}

		}

		int profit;
		for (profit = maxProfit; profit > 0; profit--) {
			if (weight[(n - 1) % 2][profit] <= capacity) {
				break;
			}
		}
		return profit;

	}
}
