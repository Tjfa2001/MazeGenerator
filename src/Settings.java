import java.awt.Color;

public class Settings {
	
	// Size settings
	public final static int FRAME_WIDTH = 1000;
	public final static int FRAME_HEIGHT = 1000;
	public final static int COLUMNS_OF_CELLS = 20;
	public final static int ROWS_OF_CELLS = 20;
	
	// Wall settings
	public final static int WALL_WIDTH = 3;
	public final static Color WALL_COLOUR = Color.BLACK;
	
	// Maze cell colour settings
	public final static Color MAZE_COLOUR = new Color(197, 229, 240);
	public final static Color STARTING_COLOUR = Color.PINK;
	public final static Color STARTING_CELL_COLOUR = Color.GREEN;
	public final static Color ENDING_CELL_COLOUR = Color.red;
	public final static Color VISITING_COLOUR = Color.cyan;
	public final static Color SOLUTION_COLOUR = Color.magenta;
	public final static Color CURRENTLY_CORRECT_COLOUR = Color.yellow;
	public final static Color CHECKED_COLOUR = Color.orange;

}
