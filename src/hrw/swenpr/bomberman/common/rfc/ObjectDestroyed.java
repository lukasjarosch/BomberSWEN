package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;
import java.io.Serializable;

/**
 * {@code ObjectDestroyed} is sent from the server to all clients, when an object on the pitch is destroyed by a bomb.
 * 
 * @author Marco Egger
 */
public class ObjectDestroyed extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	private Point position;
	private SpecialItem specialItem;
	
	public enum SpecialItem { SUPER_BOMB, MEGA_BOMB };
	
	/**
	 * @param position
	 * @param specialItem
	 */
	public ObjectDestroyed(Point position, SpecialItem specialItem) {
		setType(MessageType.OBJECT_DESTROYED);
		this.position = position;
		this.specialItem = specialItem;
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
	 * @return the specialItem
	 */
	public SpecialItem getSpecialItem() {
		return specialItem;
	}
	/**
	 * @param specialItem the specialItem to set
	 */
	public void setSpecialItem(SpecialItem specialItem) {
		this.specialItem = specialItem;
	}
}
