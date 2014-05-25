package hrw.swenpr.bomberman.server.thread;

import hrw.swenpr.bomberman.server.ServerMain;

public class LoginThread implements Runnable {

	/**
	 * The login loop which is constantly waiting for a login request
	 * by a client.
	 * It is then processed and a response is sent
	 * 
	 * @author Lukas Jarosch
	 */
	@Override
	public void run() {
		
		// Loop until the server is about to shut down
		while(ServerMain.getModel().isRunning()) {
			
			// Check for login request
			
			// acceptLogin()
			
			// Depending on the retVal of acceptLogin => addPlayer / sendError
		}
		
		// Kill tha bitch
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
		return true;
	}
	
	/**
	 * Handles the creation of a new player
	 * 
	 * @author Lukas Jarosch
	 */
	private void addPlayer() {
		
		// Log: "PLAYERNAME has requested a login. Processing..."
		
		// Add player model to the server model container
		
		// Create ClientThread
	}
}
