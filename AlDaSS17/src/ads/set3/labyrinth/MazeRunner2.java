package ads.set3.labyrinth;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class MazeRunner2 {

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
		final int gCost = 1;
		
		Cell[][] cells = new Cell[labyrinth.length][labyrinth[0].length];
		//buildCells
		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells[y].length; x++) {
				if(labyrinth[y][x])
					cells[y][x] = null;
				else {
					cells[y][x] = new Cell(x, y, endX, endY);
				}
			}
		}
		
		HashSet<Cell> closedCells = new HashSet<>();
		PriorityQueue<Cell> openCells = new PriorityQueue<>();
		Cell currCell;
		try{
			currCell = cells[startY][startX];			
		} catch (Exception e) {
			currCell = null;
		}
		if(currCell == null) return null;
		currCell.setG(0);

		while (currCell.getRow() != endX && currCell.getCol() != endY) {
			closedCells.add(currCell);
			
			for (int i = 0; i < dirs.length; i++) {
				
				int newX = translateX(currCell.getRow(), dirs[i]);
				int newY = translateY(currCell.getCol(), dirs[i]);	
				Cell surrCell = cells[newY][newX];
				
				if (surrCell != null && !closedCells.contains(surrCell)) {
					
					if (openCells.contains(surrCell)){
						if (currCell.getG() + gCost < surrCell.getG()) {
							surrCell.setG(currCell.getG() + gCost);
							surrCell.setDir(dirs[i]);
							openCells.remove(surrCell);
							openCells.add(surrCell);
						}
					}
					else {
						surrCell.setG(currCell.getG() + gCost);
						surrCell.setDir(dirs[i]);
						openCells.add(surrCell);
					}
				}
			}
			
			
			currCell = openCells.poll();
			if (currCell == null) // break if no way exists
				return null;

		}

		//now currCell is the target

		LinkedList<Direction> path = new LinkedList<Direction>();
		
		//backtrack the path through the cells
		while (currCell.getRow() != startX && currCell.getCol() != startY) {
			path.addFirst(currCell.getDir());
			int prevX = translateX(currCell.getRow(), invert(currCell.getDir()));
			int prevY = translateY(currCell.getCol(), invert(currCell.getDir()));	
			currCell = cells[prevY][prevX];
		}
			
		return path;
	}

	public static int translateX(int x, Direction dir) {
		if (dir == Direction.WEST)
			return x - 1;
		if (dir == Direction.EAST)
			return x + 1;
		return x;
	}

	public static int translateY(int y, Direction dir) {
		if (dir == Direction.NORTH)
			return y - 1;
		if (dir == Direction.SOUTH)
			return y + 1;
		return y;
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
}

class Cell2 implements Comparable<Cell> {

	private final int x, y;

	/**
	 * the movement cost from start to this cell
	 */
	private int G = 0;

	/**
	 * heuristischer abstand zum ziel
	 */
	private final int H;

	private Direction dir = null;

	public Cell2(int x, int y, int targetX, int targetY) {
		this.x = x;
		this.y = y;
		this.H = Math.abs(x - targetX) + Math.abs(y - targetY);
	}

	@Override
	public int compareTo(Cell o) {
		return this.getF() - o.getF();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getF() {
		return G + H;
	}

	public int getG() {
		return G;
	}
	
	public void setG(int g) {
		G = g;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public Direction getDir() {
		return dir;
	}

}
