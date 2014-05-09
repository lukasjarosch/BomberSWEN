package hrw.swenpr.bomberman.common.rfc;

/**
 * Header for every data packet send over socket. It contains the type of the message. Every other packet extends this class.
 * 
 * @author Marco Egger
 */
public abstract class Header {
	protected int type;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
