import java.awt.Dimension;
import javax.swing.JFrame;

public class Frame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int frameWidth;
	int frameHeight;
	BasePanel panel;
	
	public Frame() {
		getSettings();
		enactSettings();
		addBasePanel();
		setVisible();
	}
	
	// Adds the BasePanel to the frame
	public void addBasePanel() {
		BasePanel panel = new BasePanel();
		this.add(panel);
		this.panel = panel;
	}
	
	// Enacts the settings for our frame
	public void enactSettings() {
		this.setResizable(false);
		this.setSize(new Dimension(frameWidth,frameHeight));
		this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
		this.setTitle("Maze!");
	}
	
	// Retrieves settings from the Settings class
	public void getSettings() {
		frameWidth = Settings.FRAME_WIDTH;
		frameHeight = Settings.FRAME_HEIGHT;
	}
	
	// Sets the frame to visible
	public void setVisible() {
		this.setVisible(true);
	}

}
