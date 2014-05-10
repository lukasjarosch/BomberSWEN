package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * {@code UserReady} is sent from the client to the server, when the player pressed the ready button.
 * 
 * @author Marco Egger
 */
public class UserReady extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userID;

	/**
	 * @param userID
	 */
	public UserReady(int userID) {
		setType(MessageType.USER_READY);
		this.userID = userID;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
