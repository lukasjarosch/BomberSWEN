package hrw.swenpr.bomberman.common;

import java.util.EventObject;

public class BombermanEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public BombermanEvent(BombermanBaseModel model) {
		super(model);
	}
}
