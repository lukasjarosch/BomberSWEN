package hrw.swenpr.bomberman.common.rfc;

import java.awt.Point;
import java.io.Serializable;

/**
 * Represents a level with its name and size, but no further information of the level.
 * 
 * @author Marco Egger
 */
public class Level implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String filename;
	private Point size;
	
	/**
	 * @param filename
	 * @param size
	 */
	public Level(String filename, Point size) {
		this.filename = filename;
		this.size = size;
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
	/**
	 * @return the size
	 */
	public Point getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(Point size) {
		this.size = size;
	}
}
