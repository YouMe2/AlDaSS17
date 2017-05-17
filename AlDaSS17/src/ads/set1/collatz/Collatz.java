/**
 * 
 */
package ads.set1.collatz;

import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

/**
 * @author Yannik Eikmeier
 * @since 02.05.2017
 *
 */
public class Collatz {

	public static void main(String[] args) {

		CollatzNode n40 = CollatzNode.getCollatzNode(40);
		n40.printChain();

		System.out.println(new Collatz().maxCycleLength(2, 20));
		System.out.println(new Collatz().maxCycleLength(2, 200));
		System.out.println(new Collatz().maxCycleLength(2, 100000));
	}

	/**
	 * Calculates the maximum Collatz cycle length for all start values
	 * {@code x} with {@code lower <= x <= upper}.
	 * 
	 * @param lower
	 *            lower bound on the start values.
	 * @param upper
	 *            upper bound on the start values.
	 * @return maximum Collatz cycle length.
	 */
	public int maxCycleLength(int lower, int upper) {
		
		int max = -1;
		
		for (int i = lower; i <= upper; i++) {
			max = Math.max(CollatzNode.getCollatzNode(i).steps, max);		
		}

		return max;
	}

}

/**
 * @author Yannik Eikmeier
 * @since 02.05.2017
 *
 */
class CollatzNode {

	private static HashMap<Integer, CollatzNode> NODES = new HashMap<>();
	/**
	 * The basenode, with n = 1.
	 */
	public static final CollatzNode ONE = new CollatzNode(1, null, 1);

	// no getters here cuz final
	final CollatzNode next;
	final int n;
	final int steps;

	/**
	 * +--------------+
	 * | STAFF ONLY ! |
	 * +--------------+
	 * 
	 * @param n
	 * @param next
	 * @param steps
	 */
	private CollatzNode(int n, CollatzNode next, int steps) {
		this.next = next;
		this.n = n;
		this.steps = steps;
		NODES.put(n, this);
	}

	/**
	 * Returns and if necessary creates a new {@link CollatzNode} for the given
	 * n.
	 * 
	 * @param n
	 * @return the {@link CollatzNode} for the given value n
	 */
	public static CollatzNode getCollatzNode(int n) {

		if( n == 0)
			throw new IllegalArgumentException("n must be > 0 !");
		
		if (NODES.containsKey(n))
			return NODES.get(n);
		else {
			CollatzNode next = getCollatzNode(doCollatzStep(n));
			return new CollatzNode(n, next, next.steps + 1);
		}
	}

	/**
	 * perfomes a Collatz iteration
	 * 
	 * @param n 
	 * @return the following Collatznumber
	 */
	public static int doCollatzStep(int n) {
		return n % 2 == 0 ? n / 2 : 3 * n + 1;
	}

	/**
	 * prints out the chain this {@link CollatzNode} builds
	 */
	public void printChain() {

		CollatzNode node = this;

		System.out.println(node.steps + " steps: ");

		do {

			System.out.print(node.n + " -> ");
			node = node.next;

		} while (node != ONE);
		System.out.println(ONE.n);
	}

}