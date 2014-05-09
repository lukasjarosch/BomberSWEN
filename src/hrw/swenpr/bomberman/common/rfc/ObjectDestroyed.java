package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;

/**
 * {@code ObjectDestroyed} is sent from the server to all clients, when an object on the pitch is destroyed by a bomb.
 * 
 * @author Marco Egger
 */
public class ObjectDestroyed extends Header {
	private Point position;
	private int specialItem;
	
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
	 * @return the specialItem
	 */
	public int getSpecialItem() {
		return specialItem;
	}
	/**
	 * @param specialItem the specialItem to set
	 */
	public void setSpecialItem(int specialItem) {
		this.specialItem = specialItem;
	}
}
