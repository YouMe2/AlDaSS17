package ads.set3.labyrinth;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class MazeRunner3 {

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
		
		final int rowLength = labyrinth.length;
		final int colLenght = labyrinth[0].length;
		
		final int startRow = startY;
		final int startCol = startX;		
		final int targetRow = endY;
		final int targetCol = endX;
		/*
		 * oh btw...
		 * Why on earth do u guys keep messing this [x][y] vs [row][col] thing up??
		 * Why give us a X-coord for start and end, but have the maze structured in to rows and columns?
		 * Now we would need to do [y][x] and certainly does not feel right!
		 * 
		 * This f*ed me up like crazy trying to debugg this!
		 */
		
		
		
		//buildCells
		Cell[][] cells = new Cell[rowLength][colLenght];
		for (int row = 0; row < rowLength; row++) {
			for (int col = 0; col < colLenght; col++) {
				if(labyrinth[row][col])
					cells[row][col] = null;
				else {
					cells[row][col] = new Cell(col, row, targetRow, targetCol);
				}
			}
		}
		
		HashSet<Cell> closedCells = new HashSet<>();
		PriorityQueue<Cell> openCells = new PriorityQueue<>();
		
		Cell currCell;
		try{
			currCell = cells[startRow][startCol];			
		} catch (Exception e) {
			currCell = null;
		}
		if(currCell == null) return null;
		currCell.setG(0);

		while (currCell.getRow() != targetRow && currCell.getCol() != targetCol) {
			closedCells.add(currCell);
			
			alltheways: for (int i = 0; i < dirs.length; i++) {
				
				int newRow = translateRow(currCell.getRow(), dirs[i]);
				int newCol = translateCol(currCell.getCol(), dirs[i]);
				Cell surrCell = null;
				if( newRow >= 0 && newRow < rowLength && newCol >= 0 && newCol < colLenght)
					surrCell = cells[newCol][newRow];
				
				if (surrCell == null)
					continue alltheways;
				
				if (!closedCells.contains(surrCell)) {
					
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
			
			
			if (openCells.isEmpty()) // break if no way exists
				return null;
			currCell = openCells.poll();

		}

		//now currCell is the target

		LinkedList<Direction> path = new LinkedList<Direction>();
		
		//backtrack the path through the cells
		while (currCell.getRow() != startRow && currCell.getCol() != startCol) {
			path.addFirst(currCell.getDir());
			int prevRow = translateRow(currCell.getRow(), invert(currCell.getDir()));
			int prevCol = translateCol(currCell.getCol(), invert(currCell.getDir()));	
			currCell = cells[prevRow][prevCol];
		}
			
		return path;
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
}

class Cell implements Comparable<Cell> {

	private final int row, col;

	/**
	 * the movement cost from start to this cell
	 */
	private int G = 0;

	/**
	 * heuristischer abstand zum ziel
	 */
	private final int H;

	private Direction dir = null;

	public Cell(int row, int col, int targetRow, int targetCol) {
		this.row = row;
		this.col = col;
		this.H = Math.abs(row - targetRow) + Math.abs(col - targetCol);
	}

	@Override
	public int compareTo(Cell o) {
		return this.getF() - o.getF();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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
