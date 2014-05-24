package hrw.swenpr.bomberman.server.view;

import hrw.swenpr.bomberman.server.LogMessage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
	 * The window title
	 */
	private static String FRAME_TITLE = "Bomerman Server";
	
	/**
	 * Required for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link MainPanel} which holds the logging area and the server controls
	 */
	private MainPanel mainPanel = new MainPanel();
	
	/**
	 * The {@link SidebarPanel} which holds information about the current game
	 */
	private SidebarPanel sidebarPanel = new SidebarPanel();
	
	/**
	 * Constructor for the main window
	 * 
	 * @author Lukas Jarosch
	 */
	public MainWindow() {
		
		// Set basic frame attributes
		setAttributes();

		// Add panels
		add(mainPanel, BorderLayout.WEST);
		add(sidebarPanel, BorderLayout.EAST);
			
		// Set missing attributes
		pack();
		setLocationRelativeTo(null);
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
		setTitle(FRAME_TITLE);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	}
	
	/**
	 * Proxy method to add a log message to the {@link JTextArea}
	 * 
	 * @author Lukas Jarosch
	 */
	public void log(LogMessage message) {
		mainPanel.getLogArea().append(message.getLogString());
	}
}
