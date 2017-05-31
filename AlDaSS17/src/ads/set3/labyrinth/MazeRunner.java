package ads.set3.labyrinth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.security.auth.login.FailedLoginException;

public class MazeRunner {

	public static void main(String[] args) {

		boolean[][] labyrinth = new boolean[][] { 
			{true, true, true, true, true, true, true},
			{false, false, false, false, false, false, true}, 
			{true, false, true, false, true, false, true},
			{true, false, true, false, false, false, true}, 
			{true, false, true, false, true, false, true},
			{true, false, false, false, true, false, false},
			{true, true, true, true, true, true, true} };
			printMaze(labyrinth);
		System.out.println(findPath(labyrinth, 0, 1, 6, 5));

	}

	/**
	 * Finds a path through the given labyrinth from the start position to the
	 * end position using a backtracking algorithm.
	 * 
	 * @param labyrinth
	 *            a two-dimensional array containing information about the walls
	 *            of the labyrinth. {@code labyrinth[row][column]} is
	 *            {@code true} if there is a wall at that position.
	 * @param startX
	 *            the x-coordinate (or column) of the starting position
	 * @param startY
	 *            the y-coordinate (or row) of the starting position
	 * @param endX
	 *            the x-coordinate (or column) of the end position
	 * @param endY
	 *            the y-coordinate (or row) of the end position
	 * @return a list of movement directions to get from the start position to
	 *         the end position, or {@code null} if no path exists.
	 */
	public static List<Direction> findPath(boolean[][] labyrinth, int startX, int startY, int endX, int endY) {
		Direction[] dirs = Direction.values();

		final int rowLength = labyrinth.length;
		final int colLenght = labyrinth[0].length;

		final int startRow = startY;
		final int startCol = startX;
		final int targetRow = endY;
		final int targetCol = endX;
		/*
		 * oh btw... Why on earth do u guys keep messing this [x][y] vs
		 * [row][col] thing up?? Why give us a X-coord for start and end, but
		 * have the maze structured in to rows and columns? Now we would need to
		 * do [y][x] and certainly does not feel right!
		 * 
		 * This f*ed me up like crazy, trying to debugg this!
		 */

		boolean[][] whereivbeen = new boolean[rowLength][colLenght];

		// init
		LinkedList<Direction> path = new LinkedList<Direction>();
		LinkedList<WayPoint> way = new LinkedList<>();
		WayPoint startPoint = new WayPoint(startRow, startCol, null);
		way.add(startPoint);
		whereivbeen[startRow][startCol] = true;

		WayPoint currPoint = startPoint;

		while (true) {
			WayPoint nextPoint = null;
			printMaze(whereivbeen);
			alltheways: for (Direction dir : dirs) {

				int nextRow = translateRow(currPoint.getRow(), dir);
				int nextCol = translateCol(currPoint.getCol(), dir);

				if ((nextRow >= 0 && nextRow < rowLength && nextCol >= 0 && nextCol < colLenght)
						&& !whereivbeen[nextRow][nextCol] && !labyrinth[nextRow][nextCol]) {
					// new valid wayPoint

					currPoint.setDir(dir); // update pointer
					nextPoint = new WayPoint(nextRow, nextCol, dir);
					way.addLast(nextPoint);
					whereivbeen[nextRow][nextCol] = true;

					break alltheways;
				} else {
					// novalid wapoint -> try next one
				}
			}

			if (nextPoint != null) {
				// nextPoint ist valid
				path.addLast(nextPoint.getDir());
				currPoint = nextPoint;
				
				if (currPoint.getRow() == targetRow && currPoint.getCol() == targetCol) {
					return path;
				}
				
			}
			else {
				// currPoint ist sackgasse!
				if (currPoint == startPoint) // aussichtslose lage :(
					return null;
				path.removeLast();
				way.removeLast();
				currPoint = way.getLast();
				// weiter beim letzen...

			}

		}
	}

	public static int translateRow(int row, Direction dir) {
		if (dir == Direction.NORTH)
			return row - 1;
		if (dir == Direction.SOUTH)
			return row + 1;
		return row;
	}

	public static int translateCol(int col, Direction dir) {
		if (dir == Direction.WEST)
			return col - 1;
		if (dir == Direction.EAST)
			return col + 1;
		return col;
	}

	public static Direction invert(Direction dir) {
		switch (dir) {
		case NORTH:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		case EAST:
			return Direction.WEST;
		}
		return null;
	}

	private static class WayPoint {

		private final int row, col;

		private Direction dir;

		/**
		 * 
		 */
		public WayPoint(int row, int col, Direction dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}

		/**
		 * @return the row
		 */
		public int getRow() {
			return row;
		}

		/**
		 * @return the col
		 */
		public int getCol() {
			return col;
		}

		/**
		 * @return the dir
		 */
		public Direction getDir() {
			return dir;
		}

		/**
		 * @param dir
		 *            the dir to set
		 */
		public void setDir(Direction dir) {
			this.dir = dir;
		}

	}

	public static void printMaze(boolean[][] labyrinth) {

		for (int row = 0; row < labyrinth.length; row++) {
			for (int col = 0; col < labyrinth[0].length; col++) {
				System.out.print(labyrinth[row][col] ? "+" : " ");
			}
			System.out.println();
		}

	}

}