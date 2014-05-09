package hrw.swenpr.bomberman.common.rfc;

/**
 * {@code ErrorMessage} is sent from the server to one or more clients, when an error occurred.
 * 
 * @author Marco Egger
 */
public class ErrorMessage extends Header {
	/**
	 * The subtype defines, whether this error ist just a warning that has to be displayed or the complete software has to be shut down.
	 */
	private int subtype;
	private String message;
	
	public enum ErrorType { WARNING, ERROR };
	
	/**
	 * @return the subtype
	 */
	public int getSubtype() {
		return subtype;
	}
	/**
	 * @param subtype the subtype to set
	 */
	public void setSubtype(int subtype) {
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
