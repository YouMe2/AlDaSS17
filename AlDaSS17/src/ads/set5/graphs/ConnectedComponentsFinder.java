/**
 * 
 */
package ads.set5.graphs;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Yannik Eikmeier
 * @since 28.06.2017
 *
 */
public class ConnectedComponentsFinder {

	public static void main(String[] args) {
		
		int[][] graph = new int[][]{
			{0, 0, 0, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0}};
			
		int[] res = strongConnectedComponents(graph, 0);
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i]+ " ");
		}
	}
	
	/**
	 * Finds the strongly connected components of the given {@code graph} using
	 * Kosaraju's algorithm.
	 * 
	 * @param graph
	 *            The directed graph to operate on, given as an adjacency
	 *            matrix. {@code graph[i][j] == 1} if there is a directed edge
	 *            from vertex i to vertex j, {@code graph[i][j] == 0} otherwise.
	 * @param initialVertex
	 *            The vertex to start the algorithm on.
	 * @return The assignment of each node to its strong connected component. If
	 *         the entry at position {@code 3} is {@code 2} it means that vertex
	 *         3 is contained in strongly connected component 2.
	 */
	public static int[] strongConnectedComponents(int[][] graph, int initialVertex) {
		if (graph.length != graph[0].length)
			throw new IllegalArgumentException("incorrect Graph!");
		// init
		int vLength = graph.length;
		boolean[][] visited = new boolean[vLength][vLength];
		int[] dfsNum = new int[vLength];
		int[] vadder = new int[vLength];
		Arrays.fill(vadder, -1); // -1 == undefiniert
		int[] tiefpunkt = new int[vLength];
		int label = 0;
		int v = initialVertex;
		Stack<Integer> stack = new Stack<>();

		// return field:
		int group = 0;
		int[] sCC = new int[vLength];

		// aufiauf:
		workOnVertex(v, graph, visited, dfsNum, vadder, tiefpunkt, label, stack, sCC, group);

		return sCC;
	}

	/**
	 * @param v
	 * @param graph
	 * @param visited
	 * @param dfsNum
	 * @param vadder
	 * @param tiefpunkt
	 * @param label
	 * @param stack
	 * @param sCC
	 */
	private static void workOnVertex(int v, int[][] graph, boolean[][] visited, int[] dfsNum, int[] vadder,
			int[] tiefpunkt, int label, Stack<Integer> stack, int[] sCC, int group) {

		dfsNum[v] = ++label;
		tiefpunkt[v] = label;
		stack.push(v);

		search: while (true) {
			int u;
			if ((u = getNonVisitedEdge(v, graph, visited)) != -1) {
				visited[v][u] = true;

				if (dfsNum[u] == 0) {
					vadder[u] = v;
					workOnVertex(u, graph, visited, dfsNum, vadder, tiefpunkt, label, stack, sCC, group);// -> rekursiv mit u
				} else if (dfsNum[u] > dfsNum[v] || !stack.contains(u)) {
					continue search; // -> redo search mit v =v

				} else {
					tiefpunkt[v] = Math.min(tiefpunkt[v], dfsNum[u]);
					continue search; // -> redo search mit v
				}
			} else {
				// keine Kante mehr über

				if (tiefpunkt[v] == dfsNum[v]) {
					group++;
					while (true) {
						int x = stack.pop();
						sCC[x] = group;
						if (x == v)
							break;
					}
				}

				if (vadder[v] != -1) {
					tiefpunkt[vadder[v]] = Math.min(tiefpunkt[vadder[v]], tiefpunkt[v]);
					v = vadder[v];
					continue search; // -> redo search mit vadder[v]
				} else if ((u = getUnusedVertex(dfsNum)) != -1) {
					workOnVertex(u, graph, visited, dfsNum, vadder, tiefpunkt, label, stack, sCC, group);// -> rekursiv mit u
				}
			}
			//we dont realy want a while loop here do we? naaaaahh ;)
			break;
		}

	}

	/**
	 * @param dfsNum
	 * @return
	 */
	private static int getUnusedVertex(int[] dfsNum) {
		for (int i = 0; i < dfsNum.length; i++) {
			if (dfsNum[i] == 0)
				return i;
		}

		return -1;
	}

	/**
	 * @param v
	 * @param graph
	 * @param visited
	 * @return
	 */
	private static int getNonVisitedEdge(int v, int[][] graph, boolean[][] visited) {
		for (int i = 0; i < visited[v].length; i++) {
			if (!visited[v][i] && graph[v][i] == 1)
				return i;
		}
		return -1;
	}

}
