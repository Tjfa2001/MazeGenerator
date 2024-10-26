import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
	
	// Number of rows and cells in maze
	private int numRows;
	private int numCols;
	
	// GUI of the maze
	private Frame gui;
	
	// 2D Array of Maze Cells
	private MazeCell[][] cells;
	
	// ArrayList for later use
	private ArrayList<MazeCell> borderCells;
	
	// Random number generator
	private Random random;
	
	public Maze() {
		gui = createGUI();
		setUpVariables();
		generateMaze();
	}
	
	// Sets up variables
	public void setUpVariables() {
		this.cells = gui.panel.getCells();
		this.numRows = Settings.ROWS_OF_CELLS;
		this.numCols = Settings.COLUMNS_OF_CELLS;
		this.random = new Random();
	}
	
	// Creates the maze GUI
	public Frame createGUI() {
		return new Frame();
	}
	
	// Generates a new maze using Prim's Algorithm
	public void generateMaze() {
		
		// Intialise list of bordering squares
		this.borderCells = new ArrayList<MazeCell>();
		
		// Pick a starting square 
		MazeCell startingCell = pickStartingCell();
		startingCell.setStartingCell();
		
		// Get the bordering cells for the starting cell
		getBorderingCells(startingCell);

		// While the list is not empty
		while(!this.borderCells.isEmpty()) {
	        
			// Pick a random bordering cell
			int newCellIndex = pickRandomBorderCellIndex();
			MazeCell newCell = this.borderCells.get(newCellIndex);
			
			// Add the cell to the maze and remove it from the list of bordering cells
			newCell.setInMaze();
			this.borderCells.remove(newCellIndex);
			
			// Pick another random border cell
			getRandBordMazeCell(newCell);
			
			// Sleep for aesthetics
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
            
			// Get the bordering cells for this new cell
			getBorderingCells(newCell);
			
			// If there are no more border cells, set the new cell to be the final square
			if(this.borderCells.isEmpty()) {
				newCell.setEndCell();
			}
		}
		
	}
	
	// Retrieves a random, bordering maze cell
	private void getRandBordMazeCell(MazeCell newCell) {
		
		// Finds the bordering maze tiles
		ArrayList<MazeCell> borderingMazeTiles = findBorderingMaze(newCell);
		
		// Pick a random one
		int mazeIndex = pickRandomMazeCellIndex(borderingMazeTiles);
		
		// Join this cell to the maze
		MazeCell mazeCellToJoinTo = borderingMazeTiles.get(mazeIndex);
		joinCells(newCell,mazeCellToJoinTo);
		
		// Clear the list
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
		return random.nextInt(this.borderCells.size());
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
			this.borderCells.add(cell);
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
	
	// Returns the array representation of the maze
	public MazeCell[][] getCells(){
		return this.cells;
	}
}
