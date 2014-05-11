package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * {@code LevelAvailable} is sent from the server to all clients, when they are logged in (after {@link LoginOk} message).
 * It contains all available levels from which the gamemaster can choose.
 * 
 * @author Marco Egger
 */
public class LevelAvailable extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> filename;
	private ArrayList<Point> size;
	
	/**
	 * @param filename the plain filename, not the complete path
	 * @param size the size of the level on same position in the {@code ArrayList filename}
	 */
	public LevelAvailable(ArrayList<String> filename, ArrayList<Point> size) {
		setType(MessageType.LEVEL_AVAILABLE);
		this.filename = filename;
		this.size = size;
	}
	
	/**
	 * @return the filename
	 */
	public ArrayList<String> getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(ArrayList<String> filename) {
		this.filename = filename;
	}
	/**
	 * @return the size
	 */
	public ArrayList<Point> getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(ArrayList<Point> size) {
		this.size = size;
	}
}
