package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.view.MainWindow;

import javax.swing.JFrame;

public class Server extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Stores the server model
	 */
	private static ServerModel model;
	
	/**
	 * Instance of the main window
	 */
	private static MainWindow mainWindow;
	
	/**
	 * The {@link Communication} instance
	 */
	private static Communication communication;

	/**
	 * Application main method
	 * 
	 * @param args
	 * 
	 * @author Lukas Jarosch
	 */
	public static void main(String[] args) {
		new Server();
	}
	
	/**
	 * The {@link Server} constructor which will bootstrap the
	 * server application
	 * 
	 * @author Lukas Jarosch
	 */
	public Server() {
		
		// Create views
		mainWindow = new MainWindow();		
		
		// Start logging console
		MainWindow.log(new LogMessage(LEVEL.NONE, "----- Server application started -----"));
		
		// Create communication
		Server.communication = new Communication();
		
		// Instantiate the server model
		
		// Start the login thread
		
		// Register the ServerListener
	}

	/**
	 * Fetch the server model
	 * 
	 * @return The server model
	 * 
	 * @author Lukas Jarosch
	 */
	public static ServerModel getModel() {
		return model;
	}

	/**
	 * Fetch the mainWindow instance to be able to access the log method
	 * 
	 * @return {@link MainWindow}
	 * 
	 * @author Lukas Jarosch
	 */
	public static MainWindow getMainWindow() {
		return mainWindow;
	}
}
