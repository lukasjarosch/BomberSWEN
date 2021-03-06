package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code ErrorMessage} is sent from the server to one or more clients, when an error occurred.
 * The subtype defines, whether it is just a warning or the game have to be stopped.
 * 
 * @author Marco Egger
 */
public class ErrorMessage extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ErrorType subtype;
	private String message;
	
	public enum ErrorType { WARNING, ERROR };
	
	
	
	/**
	 * @param subtype
	 * @param message error message
	 */
	public ErrorMessage(ErrorType subtype, String message) {
		setType(MessageType.ERROR_MESSAGE);
		this.subtype = subtype;
		this.message = message;
	}
	/**
	 * @return the subtype
	 */
	public ErrorType getSubtype() {
		return subtype;
	}
	/**
	 * @param subtype the subtype to set
	 */
	public void setSubtype(ErrorType subtype) {
		this.subtype = subtype;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
