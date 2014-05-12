package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * {@code LevelAvailable} is sent from the server to all clients, when they are logged in (after {@link LoginOk} message).
 * It contains all available levels from which the gamemaster can choose.
 * 
 * @author Marco Egger
 */
public class LevelAvailable extends Header implements Serializable{
	private static final long serialVersionUID = 1L;

	private ArrayList<Level> level;
	
	/**
	 * @param level
	 */
	public LevelAvailable(ArrayList<Level> level) {
		setType(MessageType.LEVEL_AVAILABLE);
		this.level = level;
	}

	/**
	 * @return the level
	 */
	public ArrayList<Level> getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(ArrayList<Level> level) {
		this.level = level;
	}
}
