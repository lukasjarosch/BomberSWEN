package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.common.ServerConnection;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.thread.LoginThread;
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
	 * The {@link ServerConnection} instance
	 */
	private static ServerConnection connection;

	/**
	 * The default port on which the socket will listen on
	 */
	public static final int DEFAULT_PORT = 6969;
	
	/**
	 * The {@link LoginThread}
	 */
	private static LoginThread loginThread = new LoginThread();
	
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
		MainWindow.log(new LogMessage(LEVEL.NONE, "## Server ready :)"));
		
		Server.prepare();
	}
	
	/**
	 * Prepare the Server for startup
	 * 
	 * @author Lukas Jarosch
	 */
	public static void prepare() {

		// Create ServerConnection
		Server.connection = new ServerConnection(DEFAULT_PORT);

		// Create communication
		Server.communication = new Communication();

		// Instantiate the server model
		Server.model = new ServerModel();
	}

	/**
	 * Start the server to accept connections
	 * 
	 * @author Lukas Jarosch
	 */
	public static void start() {
		
		// Start LoginThread
		Server.loginThread.start();
	}
	
	/**
	 * Shut the server down and prepare to be restarted
	 * 
	 * @author Lukas Jarosch
	 */
	public static void shutdown() {
		loginThread = new LoginThread();
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

	/**
	 * @return the communication
	 */
	public static Communication getCommunication() {
		return communication;
	}

	/**
	 * @return the connection
	 */
	public static ServerConnection getConnection() {
		return connection;
	}

	/**
	 * Returns the current {@link LoginThread}
	 * @return
	 */
	public static LoginThread getLoginThread() {
		return loginThread;
	}

}
