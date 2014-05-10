package hrw.swenpr.bomberman.common.rfc;

import java.io.Serializable;


/**
 * {@code User} is sent from the server to every client, when a new User is logged in or a round or game ended.
 * 
 * @author Marco Egger
 */
public class User extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userID;
	private String username;
	private int score;
	private UserColor color;
	
	/**
	 * @param userID
	 * @param username
	 * @param score
	 * @param color
	 */
	public User(int userID, String username, int score, UserColor color) {
		setType(MessageType.USER);
		this.userID = userID;
		this.username = username;
		this.score = score;
		this.color = color;
	}
	
	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
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
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
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
