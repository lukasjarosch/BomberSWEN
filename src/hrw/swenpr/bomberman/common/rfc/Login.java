package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * {@code Login} is sent from a client to the server, when he tries to login.
 * 
 * @author Marco Egger
 */
public class Login extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private UserColor color;
	
	/**
	 * @param username
	 * @param color
	 */
	public Login(String username, UserColor color) {
		setType(MessageType.LOGIN);
		this.username = username;
		this.color = color;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the color
	 */
	public UserColor getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(UserColor color) {
		this.color = color;
	}

}
