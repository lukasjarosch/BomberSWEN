package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code RoundFinished} is sent from the server to every client, when a round ended. 
 * The clients have to stop the game time themselves (on client side the time is only as information for the user). 
 * It contains no further content.
 * 
 * @author Marco Egger
 */
public class RoundFinished extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public RoundFinished() {
		setType(MessageType.ROUND_FINISHED);
	}

}
