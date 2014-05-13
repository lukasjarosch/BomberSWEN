package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;

/**
 * <p> C => S</p>
 * {@code Login} is sent from a client to the server, when he tries to login. 
 * The message contains the desired username and the selected color.
 * 
 * @author Marco Egger
 */
public class Login extends Header implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private User.UserColor color;
	
	/**
	 * @param username
	 * @param color
	 */
	public Login(String username, User.UserColor color) {
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
	public User.UserColor getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(User.UserColor color) {
		this.color = color;
	}

}
