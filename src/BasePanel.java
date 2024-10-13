import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class BasePanel extends JPanel{
	
	int frameWidth;
	int frameHeight;
	int numCells;
	int numRows;
	int numCols;
	MazeCell[][] cells;
	
	public BasePanel() {
		getSettings();
		enactSettings();
		
		addCells();
		this.setVisible(true);
	}
	
	public void getSettings() {
		frameWidth = Settings.FRAME_WIDTH;
		frameHeight = Settings.FRAME_HEIGHT;
		
		numRows = Settings.ROWS_OF_CELLS;
		numCols = Settings.COLUMNS_OF_CELLS;
	}
	
	public void enactSettings() {
		cells = new MazeCell[numRows][numCols];
		this.setSize(new Dimension(frameWidth,frameHeight));
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(numRows,numCols,0,0));
	}
	
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
	
	public int calculateCellWidth() {
		return this.frameWidth / this.numCols;
	}
	
	public int calculateCellHeight() {
		return this.frameWidth / this.numRows;
	}
	
	public void addCellToPanel(MazeCell cell) {
		this.add(cell);
	}
	
	public void addCellToList(MazeCell cell, int rowIndex, int colIndex) {
		this.cells[rowIndex][colIndex] = cell;
	}
	public MazeCell[][] getCells(){
		return this.cells;
	}

}
