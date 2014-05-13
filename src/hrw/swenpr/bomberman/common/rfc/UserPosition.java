package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;
import java.io.Serializable;

/**
 * <p> C <=> S</p>
 * {@code UserPosition} is sent from the client to the server, when a user moves his player. 
 * The server forwards this message to the other clients, so they can display the new position.
 * 
 * @author Marco Egger
 */
public class UserPosition extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userID;
	private Point position;
	
	/**
	 * @param userID
	 * @param position
	 */
	public UserPosition(int userID, Point position) {
		setType(MessageType.USER_POSITION);
		this.userID = userID;
		this.position = position;
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
