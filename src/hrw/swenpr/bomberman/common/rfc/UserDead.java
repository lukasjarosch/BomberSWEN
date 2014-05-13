package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code UserDead} is sent from the server to all clients, when one user dies from a bomb.
 * 
 * @author Marco Egger
 */
public class UserDead extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userID;

	/**
	 * @param userID
	 */
	public UserDead(int userID) {
		setType(MessageType.USER_DEAD);
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
