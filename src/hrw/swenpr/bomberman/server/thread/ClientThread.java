package hrw.swenpr.bomberman.server.thread;

import java.net.Socket;

import hrw.swenpr.bomberman.common.rfc.LevelSelection;
import hrw.swenpr.bomberman.common.rfc.TimeSelection;
import hrw.swenpr.bomberman.common.rfc.UserReady;

/**
 * This thread is started for each 
 * @author Lukas
 *
 */
public class ClientThread implements Runnable {
	
	/**
	 * If the player is the game admin
	 */
	private boolean gameAdmin = false;
	
	/**
	 * Once the player dies...he's dead
	 */
	private boolean alive = true;
	
	/**
	 * The client socket
	 */
	private Socket socket = null;
	
	/**
	 * Creates the socket for communicating with the client
	 * 
	 * @author Lukas Jarosch
	 */
	public ClientThread() {
		// Create socket and set it locally
	}
	
	/**
	 * @author Lukas Jarosch
	 */
	@Override
	public void run() {		
	}

	/**
	 * Handles a {@link LevelSelection} from the game admin(!)
	 * Other players cannot select the level
	 * 
	 * @author Lukas Jarosch
	 */
	public void handleLevelSelection() {
	
		// Test if admin
		
		// Set level
		
	}
	
	/**
	 * Handles a {@link TimeSelection} from the game admin(!)
	 * Other players cannot select the time
	 * 
	 * @author Lukas Jarosch
	 */
	public void handleTimeSelection() {
	
		// Test if admin
		
		// Set time in model
	}
	
	/**
	 * Handles a {@link UserReady} message from the player
	 * 
	 * @author Lukas Jarosch
	 */
	public void handleUserReady() {
		
		// Get the server model
		
		// model.increaseReadyCount();
		
		// Test if readyCount == playerCount
		
			// Start the game
	}

	/**
	 * Is the player still alive?
	 * 
	 * @return boolean
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Set the player to dead or alive (Schrödinger....)
	 * 
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
