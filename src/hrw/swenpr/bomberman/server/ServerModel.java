package hrw.swenpr.bomberman.server;

import java.io.File;
import java.util.ArrayList;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.rfc.User;

public class ServerModel extends BombermanBaseModel {

	/**
	 * How many players are ready to start the game?
	 * If this matches the total player count the game will start
	 */
	private int readyCount = 0;
	
	/**
	 * The {@link ServerModel} constructor
	 * 
	 * @param The players participating in the game
	 * @param The level file to load
	 */
	public ServerModel(ArrayList<User> users, File level) {
		super(users, level);
	}

	/**
	 * We do not allow direct modification of the readyCount.
	 * A player can only click on 'Ready' but not 'unclick' it.
	 * 
	 * This will simply increase the current readyCount
	 * 
	 * It will not perform any validation!
	 * 
	 * @author Lukas Jarosch
	 */
	public void increaseReadyCount() {
		readyCount++;
	}
	
	/**
	 * Returns the current readyCount
	 * 
	 * @return The amount of players ready to play
	 * 
	 * @author Lukas Jarosch
	 */
	public int getReadyCount() {
		return readyCount;
	}
}
