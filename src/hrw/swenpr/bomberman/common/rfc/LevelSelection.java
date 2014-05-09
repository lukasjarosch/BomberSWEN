package hrw.swenpr.bomberman.common.rfc;

/**
 * {@code LevelSelection} is sent from the client of the gamemaster to the server, when he selected a level. The server forwards this message to the other clients.
 * 
 * @author Marco Egger
 */
public class LevelSelection extends Header {
	private int levelID;
	private String filename;
	
	/**
	 * @return the levelID
	 */
	public int getLevelID() {
		return levelID;
	}
	/**
	 * @param levelID the levelID to set
	 */
	public void setLevelID(int levelID) {
		this.levelID = levelID;
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
