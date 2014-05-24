package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.ClientThread;

import javax.swing.JFrame;

public class ServerMain extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new ServerMain();
	}
	
	public ServerMain() {
		
		// Initiate main frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bomberman");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setVisible(true);
		
		// Wait for logins until the game is started or 4 players are logged in
		while(acceptLogin()) {
			addPlayer();
		}
	}
	
	/**
	 * Waits for a login message
	 * The message is then processed and a 
	 * {@link ClientThread} is created
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @return Whether to accept further logins or not
	 */
	private boolean acceptLogin() {
		
		// Test if the game is already running => FALSE
		// If game is running: Log: "Game already running. Login rejected"
		
		// Test how many players are logged in => FALSE
		// If 4 players are already logged in: Log: "Too many players. Login rejected"
		
		// Player is ok to join 
		// => TRUE
	}
	
	/**
	 * Handles the creation of a new player
	 * 
	 * @author Lukas Jarosch
	 */
	private void addPlayer() {
		
		// Log: "PLAYERNAME has requested a login. Processing..."
		
		// Add player to the model
		
		// Create ClientThread
	}

}
