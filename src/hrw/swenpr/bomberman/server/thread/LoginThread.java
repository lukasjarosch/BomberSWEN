package hrw.swenpr.bomberman.server.thread;

import hrw.swenpr.bomberman.common.rfc.Login;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.server.LogMessage;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
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
		while(ServerMain.getModel().isServerRunning()) {
			
			// Check for login request
			
			if(requestLogin()) {
				// Send LoginOK
				ServerMain.getMainWindow().log(new LogMessage(LEVEL.INFORMATION, "Player {PLAYERNAME} logged in"));
				// addPlayer( ... );
			} else {
				// Send ErrorMessage
				ServerMain.getMainWindow().log(new LogMessage(LEVEL.INFORMATION, "Player {PLAYERNAME} was rejected"));
			}
			
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
	private boolean requestLogin() {
		
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
	 * @param Login message
	 * 
	 * @author Lukas Jarosch
	 */
	private void addPlayer(Login login) {
		
		// Log: "PLAYERNAME has requested a login. Processing..."
		
		// Add player model to the server model container
		
		// Create ClientThread
	}
	
	/**
	 * Sends a {@link User} object to all clients
	 * 
	 * @author Lukas Jarosch
	 */
	private void sendUserData() {
		
		// Create User object
		
		// Send message to all connected clients
		
	}
}
