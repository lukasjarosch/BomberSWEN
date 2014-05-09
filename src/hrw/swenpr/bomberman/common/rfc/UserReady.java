package hrw.swenpr.bomberman.common.rfc;

/**
 * {@code UserReady} is sent from the client to the server, when the player pressed the ready button.
 * 
 * @author Marco Egger
 */
public class UserReady extends Header {
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
