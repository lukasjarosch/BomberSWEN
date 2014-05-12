package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * {@code LevelSelection} is sent from the client of the gamemaster to the server, when he selected a level.
 * The server forwards this message to the other clients, so the other players can see the current selected level.
 * 
 * @author Marco Egger
 */
public class LevelSelection extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String filename;
	
	/**
	 * @param filename of the level
	 */
	public LevelSelection(String filename) {
		setType(MessageType.LEVEL_SELECTION);
		this.filename = filename;
	}
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
