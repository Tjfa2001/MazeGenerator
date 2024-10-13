import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
	
	// Number of rows and cells in maze
	int numRows;
	int numCols;
	
	// GUI of the maze
	Frame gui;
	
	// 2D Array of Maze Cells
	MazeCell[][] cells;
	
	// ArrayList for later use
	ArrayList<MazeCell> borderCells;
	
	// Random number generator
	Random random;
	
	public Maze() {
		gui = createGUI();
		setUpVariables();
		try {
			generateMaze();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setUpVariables() {
		this.cells = gui.panel.getCells();
		this.numRows = Settings.ROWS_OF_CELLS;
		this.numCols = Settings.COLUMNS_OF_CELLS;
		this.random = new Random();
	}
	
	public Frame createGUI() {
		return new Frame();
	}
	
	public void generateMaze() throws InterruptedException {
		// Pick a starting square 
		borderCells = new ArrayList<MazeCell>();
		
		MazeCell startingCell = pickStartingCell();

		startingCell.setStartingCell();
		
		getBorderingCells(startingCell);
		
		// Make a (list?) of all of the squares bordering the maze currently
		
		// While the list is not empty
		while(!borderCells.isEmpty()) {
	
			int newCellIndex = pickRandomBorderCellIndex();
			
			MazeCell newCell = borderCells.get(newCellIndex);
			newCell.setInMaze();
			borderCells.remove(newCellIndex);
			
			getRandBordMazeCell(newCell);
			
			Thread.sleep(10);

			getBorderingCells(newCell);
			if(this.borderCells.isEmpty()) {
				newCell.setEndCell();
			}
		}
		
		System.out.println("end");
		
	}
	
	// Retrieves a random, bordering maze cell
	private void getRandBordMazeCell(MazeCell newCell) {
		ArrayList<MazeCell> borderingMazeTiles = findBorderingMaze(newCell);
			
			int mazeIndex = pickRandomMazeCellIndex(borderingMazeTiles);
			MazeCell mazeCellToJoinTo = borderingMazeTiles.get(mazeIndex);
			joinCells(newCell,mazeCellToJoinTo);
			borderingMazeTiles.clear();
	}
	
	// Joins the border cell to a maze cell
	private void joinCells(MazeCell newCell, MazeCell oldCell) {
		// Location of new cell
		int newCellRow = newCell.rowIndex;
		int newCellCol = newCell.colIndex;
		
		// Location of old cell (cell in maze)
		int oldCellRow = oldCell.rowIndex;
		int oldCellCol = oldCell.colIndex;
		
		// New cell is below old cell
		if(newCellRow - oldCellRow == 1) {
			newCell.removeTopWall();
			oldCell.removeBottomWall();
		}
		
		// New cell is above old cell
		if(newCellRow - oldCellRow == -1) {
			newCell.removeBottomWall();
			oldCell.removeTopWall();
		}
		
		// New cell is right of old cell
		if(newCellCol - oldCellCol == 1) {
			newCell.removeLeftWall();
			oldCell.removeRightWall();
		}
		
		// New cell is above of old cell
		if(newCellCol - oldCellCol == -1) {
			newCell.removeRightWall();
			oldCell.removeLeftWall();
		}
	}
	
	// Picks a random maze cell next to border cell
	private int pickRandomMazeCellIndex(ArrayList<MazeCell> list) {
		return random.nextInt(list.size());
	}
	
	// Finds all the maze cells bordering the border cell
	private ArrayList<MazeCell> findBorderingMaze(MazeCell cell) {
		
		// ArrayList to store candidate cells
		ArrayList<MazeCell> candidateCells = new ArrayList<>();
		
		// Location of border cell
		int colIndex = cell.colIndex;
		int rowIndex = cell.rowIndex;
		
		if (colIndex != 0 ) {
			// Left cell
			MazeCell left = cells[rowIndex][colIndex-1];
			if(left.isInMaze()) {
				candidateCells.add(left);
			}
		} 
		
		if (rowIndex != 0) {
			// Above cell
			MazeCell above = cells[rowIndex-1][colIndex];
			processCell(above);
			if(above.isInMaze()) {
				candidateCells.add(above);
			}
		} 
		
		if (colIndex != numCols-1) {
			// Right cell
			MazeCell right = cells[rowIndex][colIndex+1];
			processCell(right);
			if(right.isInMaze()) {
				candidateCells.add(right);
			}
		} 
		
		if (rowIndex != numRows-1) {
			// Below cell
			MazeCell below = cells[rowIndex+1][colIndex];
			processCell(below);
			if(below.isInMaze()) {
				candidateCells.add(below);
			}
		}
		
		return candidateCells;
	}
	
	// Picks the cell from which the maze will be created
	public MazeCell pickStartingCell() {
		int rowIndex = random.nextInt(numRows);
		int colIndex = random.nextInt(numCols);
		MazeCell startingCell = cells[rowIndex][colIndex];
		return startingCell;
	}
	
	// Picks a random border cell index
	public int pickRandomBorderCellIndex() {
		return random.nextInt(borderCells.size());
	}
	
	// Finds the bordering cells next to a maze cell
	public void getBorderingCells(MazeCell cell) {
		
		int colIndex = cell.colIndex;
		int rowIndex = cell.rowIndex;
		
		if (colIndex != 0 ) {
			// Left cell
			MazeCell left = cells[rowIndex][colIndex-1];
			processCell(left);
		} 
		
		if (rowIndex != 0) {
			// Above cell
			MazeCell above = cells[rowIndex-1][colIndex];
			processCell(above);
		} 
		
		if (colIndex != numCols-1) {
			// Right cell
			MazeCell right = cells[rowIndex][colIndex+1];
			processCell(right);
		} 
		
		if (rowIndex != numRows-1) {
			// Below cell
			MazeCell below = cells[rowIndex+1][colIndex];
			processCell(below);
		}

	}
	
	// Processes maze cells
	public void processCell(MazeCell cell) {
		boolean CellInMaze = checkIfInMaze(cell);
		boolean CellInBorder = checkIfInBorder(cell);
		
		if(!CellInMaze && !CellInBorder) {
			borderCells.add(cell);
			cell.setInBorder();
			cell.setBackground(Color.MAGENTA);
		}
	}
	
	// Checks if a cell is in the maze
	public boolean checkIfInMaze(MazeCell cell) {
		return cell.isInMaze();
	}
	
	// Checks if a cell is bordering the maze
	public boolean checkIfInBorder(MazeCell cell) {
		return cell.isInBorder();
	}
}
