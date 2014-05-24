package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.common.rfc.LevelSelection;
import hrw.swenpr.bomberman.common.rfc.TimeSelection;

/**
 * This thread is started for each 
 * @author Lukas
 *
 */
public class ClientThread implements Runnable {
	

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
}
