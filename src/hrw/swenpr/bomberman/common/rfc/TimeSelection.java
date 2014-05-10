package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * {@code TimeSelection} is sent from the client of the gamemaster to the server, when he selected the time for the game. 
 * The server forwards this message to the other clients.
 * 
 * @author Marco Egger
 */
public class TimeSelection extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int time;

	/**
	 * @param time duration of the game
	 */
	public TimeSelection(int time) {
		setType(MessageType.TIME_SELECTION);
		this.time = time;
	}

	/**
	 * @return the time in minutes
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set in minutes
	 */
	public void setTime(int time) {
		this.time = time;
	}
}
