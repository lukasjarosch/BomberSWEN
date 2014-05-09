package hrw.swenpr.bomberman.common.rfc;

/**
 * {@code LoginOk} is sent from the server to a client, when the login was successful.
 * 
 * @author Marco Egger
 */
public class LoginOk extends Header {
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
