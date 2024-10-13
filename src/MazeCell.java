import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MazeCell extends JPanel {
	
	// Row and column index of maze cell in BasePanel
	int rowIndex;
	int colIndex;
	
	// Width and height of maze cell and the width of walls between them
	int width;
	int height;
	int wallWidth;
	
	// Colour settings
	Color wallColour;
	Color mazeColour;
	Color startingColour;
	Color startCellColour;
	Color endCellColour;
	
	// State of maze cell
	boolean isInMaze = false;
	boolean isInBorder = false;
	
	// Whether cell has these walls or not
	boolean topWall = true;
	boolean bottomWall = true;
	boolean rightWall = true;
	boolean leftWall = true;
	
	public MazeCell(int width, int height) {		
		getSettings();
		sizeSettings(width,height);
		setStartingColour();
		this.setVisible(true);
	}
	
	// Sets the starting colour of the cell
	private void setStartingColour() {
		this.setBackground(this.startingColour);
	}
	
	// Sorts out any sizing settings
	private void sizeSettings(int width, int height) {
		this.setSize(new Dimension(width,height));
		this.width = width;
		this.height = height;
	}
	
	// Retrieves settings from the Settings class
	public void getSettings() {
		// Get width and height of panels
		this.wallWidth      = Settings.WALL_WIDTH;
		// Get colour of panel before visit, after visit and when finished
		this.wallColour      = Settings.WALL_COLOUR;
		this.mazeColour      = Settings.MAZE_COLOUR;
		this.startingColour  = Settings.STARTING_COLOUR;
		this.startCellColour = Settings.STARTING_CELL_COLOUR;
		this.endCellColour   = Settings.ENDING_CELL_COLOUR;
	}
	
	// Changes state of cell to signify it is bordering the maze
	public void setInBorder() {
		this.isInBorder = true;
	}
	
	// Changes state of cell to signify it is not bordering the maze
	public void removeFromBorder() {
		this.isInBorder = false;
	}
	
	// Returns whether the cell is bordering the maze
	public boolean isInBorder() {
		return this.isInBorder;
	}
	
	// Changes state of cell to signify it is now in the maze
	public void setInMaze() {
		this.isInMaze = true;
		removeFromBorder();
		this.setBackground(this.mazeColour);
	}
	
	// Changes state of cell to starting cell
	public void setStartingCell() {
		this.isInMaze = true;
		this.setBackground(this.startCellColour);
	}
	
	public void setEndCell() {
		this.isInMaze = true;
		this.setBackground(this.endCellColour);
	}
	
	// Returns whether the cell is in the maze
	public boolean isInMaze() {
		return this.isInMaze;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		adjustWallSettings(g2d);
		
		if(leftWall) {
			drawLeftWall(g2d);
		}
		if(rightWall) {
			drawRightWall(g2d);
		}
		if(topWall) {
			drawTopWall(g2d);
		}
		if(bottomWall) {
			drawBottomWall(g2d);
		}
		repaint();
	}
	
	private void adjustWallSettings(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(this.wallWidth));
		g2d.setColor(this.wallColour);
	}
	
	private void drawLeftWall(Graphics g) {
		g.drawLine(0, 0, 0, this.height);
	}
	
	private void drawRightWall(Graphics g) {
		g.drawLine(this.width-this.wallWidth, 0, this.width-this.wallWidth, this.height);
	}
	
	private void drawTopWall(Graphics g) {
		g.drawLine(0, 0, this.width, 0);
	}
	
	private void drawBottomWall(Graphics g) {
		g.drawLine(0, this.height-this.wallWidth, this.width, this.height-this.wallWidth);
	}
	
	public void removeTopWall() {
		this.topWall = false;
	}
	
	public void removeBottomWall() {
		this.bottomWall = false;
	}
	
	public void removeLeftWall() {
		this.leftWall = false;
	}
	
	public void removeRightWall() {
		this.rightWall = false;
	}
}
