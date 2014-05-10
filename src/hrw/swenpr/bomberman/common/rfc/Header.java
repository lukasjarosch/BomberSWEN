package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * Header for every data packet send over socket. It contains the type of the message. Every other packet extends this class.
 * 
 * @author Marco Egger
 */
public abstract class Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected MessageType type;

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}
	
	/**
	 * {@code Static} function for determining the type of the message.
	 * 
	 * @param msg message object received
	 * @return {@link MessageType}
	 */
	public static MessageType getMessageType(Object msg) {
		Header header = (Header) msg;
		return header.getType();
	}
}
