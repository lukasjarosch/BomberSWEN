package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code GameOver} is sent from the server to all clients, when the complete game ends. It contains no further content.
 * 
 * @author Marco Egger
 */
public class GameOver extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public GameOver() {
		setType(MessageType.GAME_OVER);
	}
}
