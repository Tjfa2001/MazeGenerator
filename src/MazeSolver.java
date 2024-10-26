import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeSolver {
	
	// MazeSolver useful variables
	private Maze maze;
	private MazeCell[][] cells;
	private Stack<MazeCell> steps;
	
	// Retrieved Settings
	private int numRows;
	private int numCols;
	private Color startColour;
	private Color endColour;
	private Color visitColour;
	private Color solutionColour;
	private Color mazeColour;
	private Color currentlyCorrect;
	private Color checkedColour;
	
	// Random object used to pick random directions
	private Random random;
	
	public MazeSolver(Maze maze) {
		
		this.maze = maze;
		getSettings();
		applySettings();
		solve();
	}
	
	// Gets the settings from the settings class
	public void getSettings() {
		this.numRows = Settings.ROWS_OF_CELLS;
		this.numCols = Settings.COLUMNS_OF_CELLS;
		this.startColour = Settings.STARTING_CELL_COLOUR;
		this.endColour = Settings.ENDING_CELL_COLOUR;
		this.visitColour = Settings.VISITING_COLOUR;
		this.solutionColour = Settings.SOLUTION_COLOUR;
		this.mazeColour = Settings.MAZE_COLOUR;
		this.currentlyCorrect = Settings.CURRENTLY_CORRECT_COLOUR;
		this.checkedColour = Settings.CHECKED_COLOUR;
	}
	
	// Applies additional settings
	public void applySettings() {
		this.steps = new Stack<>();
		this.cells = maze.getCells();
		this.random = new Random();
	}
	
	// Solves the maze
	public void solve() {
		
		// Find starting square and visit it
		MazeCell startingSquare = getStartingSquare();
		steps.push(startingSquare);
		startingSquare.visited = true;
		
		// Get available adjacent squares
		ArrayList<MazeCell> options;
		MazeCell cell = startingSquare;

		// Set finalSquare to false
		boolean finalSquare = false;
		// While not having found the final square
		while(!finalSquare) {
			
			// Get adjacent unvisited squares
			options = getAdjacentUnvisitedSquares(cell);
			
			// Sleeping for aesthetic purposes
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
			
			// If there are multiple options...
			if(!options.isEmpty()) {
				
				// Pick a random route
				cell = pickRandomRoute(options);
				// Visit that cell
				visit(cell);
				// Checks if the cell is the end cell
				if(cell.isEndCell()) {
					cell.setBackground(this.endColour);
					printSolution();
					// Stop solving
					finalSquare = true;
					break;
				}
				
				// If the cell is a deadend and not the starting cell
				if(deadend(cell) && cell.isStartingCell() == false) {
					// Set it to 
					cell.setBackground(this.checkedColour);
					steps.pop();
				}

			} else {
				if(!cell.isStartingCell() && !cell.isStartingCell()) {
					cell.setBackground(this.checkedColour);
				}
				if(previousHasOptions()) {
					cell = steps.peek();
				} else {
					cell = steps.pop();
				}
			}
			
			// Paint currently best solution
			if(!cell.isStartingCell()) {
				cell.setBackground(this.currentlyCorrect);
			}
			
	
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}

	}
	
	// Checks whether the previous step has any new options
	public boolean previousHasOptions() {
		return !getAdjacentUnvisitedSquares(this.steps.peek()).isEmpty();
	}
	
	// Prints the found solution onto the maze
	public void printSolution() {
		// For each square in the grid, set it to blank, unless it is the starting cell or end cell
		for (int row = 0; row < this.numRows ; row++ ) {
			for (int col = 0; col < this.numCols ; col++) {
				MazeCell cell = this.cells[row][col];
				if(nonStartNonEnd(cell)) {
					cell.setBackground(this.mazeColour);
				} 
			}
		}
		
		// Iterates through the steps taken to display a solution
		while(!this.steps.isEmpty()) {
			MazeCell cell = this.steps.pop();
			if(nonStartNonEnd(cell)) {
				cell.setBackground(this.solutionColour);
			} 
		}
	}
	
	// Checks if the cell is neither the start nor the end cell
	public boolean nonStartNonEnd(MazeCell cell) {
		return !cell.isEndCell() && !cell.isStartingCell();
	}
	
	// Checks whether a cell is a deadend
    public boolean deadend(MazeCell cell) {
    	// Counts the number of walls surrounding the cell
		int numWalls = 0;
		// Sees whether the cell has left, right, bottom and top walls
		if(cell.leftWall) {
			numWalls+=1;
		}
		if(cell.rightWall) {
			numWalls+=1;
		}
		if(cell.bottomWall) {
			numWalls+=1;
		}
		if(cell.topWall) {
			numWalls+=1;
		}
		// If there are 3 walls, then it must be a deadend
		if(numWalls==3) {
			return true;
		} else {
			return false;
		}
	}
	
    // Visits the cell
	public void visit(MazeCell cell) {
		
		// Colours the cell according to what type of cell it is
		if(cell.isStartingCell()) {
			cell.setBackground(this.startColour);
		} else if (cell.isEndCell()) {
			cell.setBackground(this.endColour);
		} else {
			cell.setBackground(this.visitColour);
		}
		
		// Sets cell to a visited state
		cell.visited = true;
		
		// Adds step to list of steps taken
		this.steps.push(cell);
	}
	
	// Picks a random route from current square
	public MazeCell pickRandomRoute(ArrayList<MazeCell> options) {
		
		// Pick a random index, retrieve that cell
		int index = this.random.nextInt(options.size());
		MazeCell randomRoute = options.get(index);
		
		return randomRoute;
	}
	
	// Gets adjacent unvisited squares for a given cell
	public ArrayList<MazeCell> getAdjacentUnvisitedSquares(MazeCell cell){
		
		// Initialise an empty array list to store adjacent unvisited square
		ArrayList<MazeCell> availableRoutes = new ArrayList<>();
		
		// Get current cell position
		int colIndex = cell.colIndex;
		int rowIndex = cell.rowIndex;
		
		
		if (colIndex != 0 ) {
			// Left cell
			MazeCell left = this.cells[rowIndex][colIndex-1];
			if(!left.visited && !left.rightWall) {
				availableRoutes.add(left);
			}
		} 
		
		if (rowIndex != 0) {
			// Above cell
			MazeCell above = this.cells[rowIndex-1][colIndex];
			if(!above.visited && !above.bottomWall) {
				availableRoutes.add(above);
			}
		} 
		
		if (colIndex != this.numCols-1) {
			// Right cell
			MazeCell right = this.cells[rowIndex][colIndex+1];
			if(!right.visited && !right.leftWall) {
				availableRoutes.add(right);
			}
		} 
		
		if (rowIndex != this.numRows-1) {
			// Below cell
			MazeCell below = this.cells[rowIndex+1][colIndex];
			if(!below.visited && !below.topWall) {
				availableRoutes.add(below);
			}
		}
		return availableRoutes;
	}
	
	
	// Finds the starting cell in the maze
 	public MazeCell getStartingSquare() {
		
		// Iterates over all cells to check whether it is the starting cell
		for(int row = 0; row < this.numRows ; row++) {
			for(int col = 0; col < this.numCols ; col++) {
				MazeCell testCell = this.cells[row][col];
				if(testCell.isStartingCell()) {
					return testCell;
				}
			}
		}
		return null;
	}
}
