package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;
import java.io.Serializable;

/**
 * <p> C <=> S</p>
 * {@code Bomb} is sent from the client to the server, when the user places a bomb.
 * The server stores the bomb in his model, and a timer triggers when it explodes and {@link ObjectDestroyed} and/or {@link UserDead}. 
 * The explosion range is calculated on every client itself.
 * The server forwards this message to the other clients, so they can display the bomb.
 * 
 * @author Marco Egger
 */
public class Bomb extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userID;
	private BombType bombType;
	private Point position;
	private float time;
	
	/**
	 * Defines the bomb types.
	 */
	public enum BombType {
		NORMAL_BOMB,
		SUPER_BOMB,
		MEGA_BOMB
	}
	
	/**
	 * @param userID of the user who placed the bomb
	 * @param bombID unique to identify the bomb
	 * @param position of the bomb
	 * @param time until bomb explode, random between 2-3 seconds
	 */
	public Bomb(int userID, BombType bombType, Point position, float time) {
		setType(MessageType.BOMB);
		this.userID = userID;
		this.bombType = bombType;
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
	public BombType getBombType() {
		return bombType;
	}
	/**
	 * @param bombID the bombID to set
	 */
	public void setBombType(BombType bombType) {
		this.bombType = bombType;
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
	 * @return the time in milliseconds
	 */
	public float getTime() {
		return time;
	}
	/**
	 * @param time the time to set in milliseconds
	 */
	public void setTime(float time) {
		this.time = time;
	}
}
