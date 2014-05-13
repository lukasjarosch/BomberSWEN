package hrw.swenpr.bomberman.common;

import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EventObject;

public class BombEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	protected int userID;
	protected BombType type;
	protected Point position;
	protected ArrayList<Point> explosion;
	
	/**
	 * Creates an event for a bomb explosion.
	 * 
	 * @param source the model
	 * @param userID the userID of the player who placed the bomb
	 * @param type the type of the bomb
	 * @param position the position of the bomb
	 */
	public BombEvent(BombermanBaseModel source, int userID, BombType type, Point position, ArrayList<Point> explosion) {
		super(source);
		this.userID = userID;
		this.type = type;
		this.position = position;
		this.explosion = explosion;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @return the type
	 */
	public BombType getType() {
		return type;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @return the explosion
	 */
	public ArrayList<Point> getExplosion() {
		return explosion;
	}
}
