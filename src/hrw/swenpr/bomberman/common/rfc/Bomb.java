package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;
import java.io.Serializable;

/**
 * {@code Bomb} is sent from the client to the server, when the user places a bomb.
 * The server forwards this message to the other clients.
 * 
 * @author Marco Egger
 */
public class Bomb extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userID;
	private int bombID;
	private Point position;
	private float time;
	
	/**
	 * @param userID of the user who placed the bomb
	 * @param bombID unique to identify the bomb
	 * @param position of the bomb
	 * @param time until bomb explode
	 */
	public Bomb(int userID, int bombID, Point position, float time) {
		setType(MessageType.BOMB);
		this.userID = userID;
		this.bombID = bombID;
		this.position = position;
		this.time = time;
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
	 * @return the bombID
	 */
	public int getBombID() {
		return bombID;
	}
	/**
	 * @param bombID the bombID to set
	 */
	public void setBombID(int bombID) {
		this.bombID = bombID;
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
	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}
}
