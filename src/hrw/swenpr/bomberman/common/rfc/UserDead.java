package hrw.swenpr.bomberman.common.rfc;

/**
 * {@code UserDead} is sent from the server to all clients, when one user dies from a bomb.
 * 
 * @author Marco Egger
 */
public class UserDead extends Header {
	private int userID;

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
