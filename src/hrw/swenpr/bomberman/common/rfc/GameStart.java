package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * {@code GameStart} is sent from the server to all clients, when every player send a {@link UserReady} message and the game is starting. It has no further content.
 * 
 * @author Marco Egger
 */
public class GameStart extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public GameStart() {
		setType(MessageType.GAME_START);
	}

}
