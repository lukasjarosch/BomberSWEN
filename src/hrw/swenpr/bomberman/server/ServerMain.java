package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.view.MainWindow;

import javax.swing.JFrame;

public class ServerMain extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Instance of the main window frame
	 */
	private MainWindow mainWindow = null;
	
	/**
	 * Main method to start the server application
	 * 
	 * @author Lukas Jarosch
	 * @param args
	 */
	public static void main(String[] args) {
		new ServerMain();
	}

	/**
	 * Bootstrap the server application
	 * 
	 * @author Lukas Jarosch
	 */
	public ServerMain() {
		
		// Create MainWindow
		mainWindow = new MainWindow();	
	}

}
