package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;

/**
 * {@code UserPosition} is sent from the client to the server, when a user moves his player. 
 * The server forwards this message to the other clients.
 * 
 * @author Marco Egger
 */
public class UserPosition extends Header {
	private int userID;
	private Point position;
	
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
	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
}
