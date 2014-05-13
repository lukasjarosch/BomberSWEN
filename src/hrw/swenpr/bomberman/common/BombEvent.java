package hrw.swenpr.bomberman.common;

import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EventObject;

/**
 * Event for a bomb explosion. This event is triggered when a bomb explodes. It contains the userID, type of the bomb, 
 * position and all fields that are affected by the explosion. It does NOT trigger any event or destroy objects. 
 * Also it adds fields that are not reached in "reality" because they are behind undestroyable objects.
 * 
 * @author Marco Egger
 */
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
	 * @return all fields that could be reached by the bomb (does NOT pay attention to objects in the way of the explosion)
	 */
	public ArrayList<Point> getExplosion() {
		return explosion;
	}
}
