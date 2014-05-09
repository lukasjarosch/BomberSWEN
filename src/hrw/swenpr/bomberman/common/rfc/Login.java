package hrw.swenpr.bomberman.common.rfc;

/**
 * {@code Login} is sent from a client to the server, when he tries to login.
 * 
 * @author Marco Egger
 */
public class Login extends Header {
	private String username;
	/**
	 * <p>The color of the character.</p>
	 * 
	 * <p>
	 * Red: 0<br>
	 * Green: 1<br>
	 * Blue: 2<br>
	 * Yellow: 3<br>
	 * </p>
	 * 
	 */
	private int color;
	
	public enum Colors {RED, GREEN, BLUE, YELLOW};
	
	
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
	public int getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

}
