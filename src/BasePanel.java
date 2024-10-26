import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class BasePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	// Settings for the size of basepanel
	private int frameWidth;
	private int frameHeight;
	
	// Settings for numbers of rows and columns
	private int numRows;
	private int numCols;
	
	// Array of the maze
	private MazeCell[][] cells;
	
	public BasePanel() {
		getSettings();
		enactSettings();
		addCells();
		setVisible();
	}
	
	// Makes the base panel visible
	public void setVisible() {
		this.setVisible(true);
	}
	
	// Gets the settings from the Settings class
	public void getSettings() {
		// Frame width and height
		this.frameWidth = Settings.FRAME_WIDTH;
		this.frameHeight = Settings.FRAME_HEIGHT;
		// Number of rows and columns in frame
		this.numRows = Settings.ROWS_OF_CELLS;
		this.numCols = Settings.COLUMNS_OF_CELLS;
	}
	
	// Enacts the settings
	public void enactSettings() {
		cells = new MazeCell[numRows][numCols];
		this.setSize(new Dimension(frameWidth,frameHeight));
		//this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(numRows,numCols,0,0));
	}
	
	// Add cells to base panel
	public void addCells() {
		// Find the necessary height and width of the cells
		int cell_width = calculateCellWidth();
	    int cell_height = calculateCellHeight();
	    
		// Populate the base panel with cells
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col <numCols; col++) {
				// Create a new maze cell
				MazeCell cell = new MazeCell(cell_width,cell_height);
				// Pass the cells its coordinates within the grids
				cell.rowIndex = row;
				cell.colIndex = col;
				// Add cell to BasePanel
				addCellToPanel(cell);
				// Add cell to list of cells
				addCellToList(cell,row,col);
			}
		}
	}
	
	// Calculates necessary cell width
	public int calculateCellWidth() {
		return this.frameWidth / this.numCols;
	}
	
	// Calculates necessary cell height
	public int calculateCellHeight() {
		return this.frameWidth / this.numRows;
	}
	
	// Adds a mazecell to the basepanel
	public void addCellToPanel(MazeCell cell) {
		this.add(cell);
	}
	
	// Adds a mazecell to the list of mazecells
	public void addCellToList(MazeCell cell, int rowIndex, int colIndex) {
		this.cells[rowIndex][colIndex] = cell;
	}
	
	// Returns the maze in array format
	public MazeCell[][] getCells(){
		return this.cells;
	}

}
