package hrw.swenpr.bomberman.common;

import java.util.EventObject;

/**
 * This event triggers, when anything on the pitch changed. E.g. a player moved, a bomb placed or SpecialItem dropped.
 * 
 * @author Marco Egger
 */
public class BombermanEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public BombermanEvent(BombermanBaseModel model) {
		super(model);
	}
}
