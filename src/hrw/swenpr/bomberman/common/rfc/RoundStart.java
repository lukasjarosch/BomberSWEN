package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code RoundStart} is sent from the server to every client, when a new round starts and a new level can be selected by the gamemaster.
 * The clients have to restart themselves the timer of game duration (on client side the time is only as information for the user).
 * It contains no further content.
 * 
 * @author Marco Egger
 */
public class RoundStart extends Header implements Serializable{
	private static final long serialVersionUID = 1L;

	public RoundStart() {
		setType(MessageType.ROUND_START);
	}
}
