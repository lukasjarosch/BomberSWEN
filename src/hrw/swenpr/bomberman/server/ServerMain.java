package hrw.swenpr.bomberman.server;

import javax.swing.JFrame;

public class ServerMain extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Stores the server model
	 */
	private static ServerModel model;

	/**
	 * Application main method
	 * 
	 * @param args
	 * 
	 * @author Lukas Jarosch
	 */
	public static void main(String[] args) {
		new ServerMain();
	}
	
	/**
	 * The {@link ServerMain} constructor which will bootstrap the
	 * server application
	 * 
	 * @author Lukas Jarosch
	 */
	public ServerMain() {
		
		// Initiate main frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bomberman");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setVisible(true);
		
		// instantiate the server model
		
		// Start the login thread
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
}
