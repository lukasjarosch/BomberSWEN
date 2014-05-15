package hrw.swenpr.bomberman.server.view;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

	/**
	 * Width of the server window
	 */
	private static int FRAME_WIDTH = 800;
	
	/**
	 * Height of the server window
	 */
	private static int FRAME_HEIGHT = 500;
	
	/**
	 * Required for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the main window
	 * 
	 * @author Lukas Jarosch
	 */
	public MainWindow() {
		setAttributes();
		pack();
		setVisible(true);
		requestFocus();
	}
	
	/**
	 * Set the default attributes of the server frame
	 * 
	 * @author Lukas Jarosch
	 */
	private void setAttributes() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Bomberman Server");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	}
}
