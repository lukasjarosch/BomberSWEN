package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code LoginOk} is sent from the server to a client, when the login was successful.
 * 
 * @author Marco Egger
 */
public class LoginOk extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userID;

	/**
	 * @param userID
	 */
	public LoginOk(int userID) {
		setType(MessageType.LOGIN_OK);
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
