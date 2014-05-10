package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * {@code RoundStart} is sent from the server to every client, when a new round starts. It contains no further content.
 * 
 * @author Marco Egger
 */
public class RoundStart extends Header implements Serializable{
	private static final long serialVersionUID = 1L;

	public RoundStart() {
		setType(MessageType.ROUND_START);
	}
}
