package hrw.swenpr.bomberman.server;

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
		
	}
	
	/**
	 * Handles a {@link TimeSelection} from the game admin(!)
	 * Other players cannot select the time
	 * 
	 * @author Lukas Jarosch
	 */
	public void handleTimeSelection() {
		
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
}
