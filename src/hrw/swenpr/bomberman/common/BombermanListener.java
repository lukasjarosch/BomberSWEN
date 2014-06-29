package hrw.swenpr.bomberman.common;

import java.util.EventListener;

/**
 * Interface for listener of {@link BombermanBaseModel} and all extending classes.
 * 
 * @author Marco Egger
 */
public interface BombermanListener extends EventListener {

	/**
	 * Triggered when a bomb exploded.
	 * 
	 * @param event the event
	 */
	public void bombExplode(BombEvent event);
}
