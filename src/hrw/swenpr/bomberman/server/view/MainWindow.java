package hrw.swenpr.bomberman.server.view;

import hrw.swenpr.bomberman.server.LogMessage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow extends JFrame {

	/**
	 * Width of the server window
	 */
	public static int FRAME_WIDTH = 800;
	
	/**
	 * Height of the server window
	 */
	public static int FRAME_HEIGHT = 500;
	
	/**
	 * The window title
	 */
	private static String FRAME_TITLE = "Bomberman Server";
	
	/**
	 * Required for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link MainPanel} which holds the logging area and the server controls
	 */
	private static MainPanel mainPanel = new MainPanel();
	
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
		add(mainPanel, BorderLayout.CENTER);
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
		setTitlePrefix("OFFLINE");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setIconImage(new ImageIcon("./icons/server_frame_icon.png").getImage());
		
		// Set Look and Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException |InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Proxy method to add a log message to the {@link JTextArea}
	 * 
	 * @author Lukas Jarosch
	 */
	public static void log(LogMessage message) {
		mainPanel.getLogArea().append(message.getLogString());
	}
	
	/**
	 * The prefix which is shown after the actual Window title
	 * 
	 * @param prefix
	 * 
	 * @author Lukas Jarosch
	 */
	public void setTitlePrefix(String prefix) {
		setTitle(FRAME_TITLE + " - " + prefix);
	}
	
	public static MainPanel getMainPanel() {
		return mainPanel;
	}
}
