package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <=> S
 * <p>{@link UserRemove} is sent from a client to the server, when he leaves the game/closes the software.
 * The server forwards this message to the other clients to update the user tables.
 * 
 * @author Marco Egger
 */
public class UserRemove extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userID;

	/**
	 * @param userID
	 */
	public UserRemove(int userID) {
		setType(MessageType.USER_REMOVE);
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
