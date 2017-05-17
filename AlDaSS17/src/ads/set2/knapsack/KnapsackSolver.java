package ads.set2.knapsack;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Yannik Eikmeier
 * @since 17.05.2017
 *
 */
public class KnapsackSolver {

	public static void main(String[] args) {
		
		Item i0 = new Item(1, 1);
		Item i1 = new Item(1, 2);
		Item i2 = new Item(2, 3);
		Item i3 = new Item(3, 4);
		Item i4 = new Item(1, 4);
		Item i5 = new Item(2, 1);
		
		
		Packing p = pack(new Item[]{i0,i1,i2,i3,i4,i5}, 6);
	}
	
	
	/**
	 * Solves the knapsack problem using the dynamic programming algorithm
	 * introduced in the lecture.
	 * 
	 * @param items
	 *            the items available to be packed into the knapsack.
	 * @param capacity
	 *            the maximum weight allowed for the knapsack.
	 * @return a packing with maximum profit which contains the items to pack
	 *         into the knapsack as well as the profit yielded by doing so.
	 */
	public static Packing pack(final Item[] items, final int capacity) {

		final int n = items.length;
		final int infinit = capacity + 1;
		int maxProfit = 0;
		for (Item i : items)
			maxProfit += i.getProfit();

		int[][] weight = new int[n][maxProfit + 1];
		// field[0][0] = 0;

		for (int t = 1; t <= maxProfit; t++) {
			if (items[0].getProfit() != t)
				weight[0][t] = infinit;
			else
				weight[0][t] = items[0].getWeight();
		}

		for (int i = 1; i < weight.length; i++) {
			for (int profit = 0; profit < weight[0].length; profit++) {

				Item item = items[i];
				int w = item.getWeight();
				int p = item.getProfit();

				if (profit < p || weight[i - 1][profit] < weight[i - 1][profit - p] + w)
					// keine verbesserung
					weight[i][profit] = weight[i - 1][profit];
				else
					// verbesserung durch neues item
					weight[i][profit] = weight[i - 1][profit - p] + w;
			
				
			}
			

		}

		Packing pack = new Packing();
		Collection<Item> resItems = pack.getItems();
		int profit;
		for (profit = maxProfit; profit > 0; profit--) {
			if (weight[n - 1][profit] <= capacity){
				System.out.println(profit);
				
				break;
			}
		}
		pack.setProfit(profit);
		for (int i = n - 1; i >= 0; i--) {

			if ((i > 0 && weight[i - 1][profit] > weight[i][profit]) || (i == 0 && profit != 0)) {
				resItems.add(items[i]);
				profit -= items[i].getProfit();
			}

		}

		return pack;
	}

}
