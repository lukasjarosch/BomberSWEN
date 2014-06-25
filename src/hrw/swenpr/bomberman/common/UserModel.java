package hrw.swenpr.bomberman.common;

import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.User.UserColor;

import java.awt.Point;

/**
 * User class for the model. Provides constructor to easily create this class
 * out of a {@link User} class with a {@link Point}.
 * 
 * @author Marco Egger
 */
public class UserModel {

	protected int userID;
	protected String username;
	protected UserColor color;
	protected Point position;
	protected boolean ready = false;

	/**
	 * @param userID
	 * @param username
	 * @param color
	 * @param position
	 */
	public UserModel(int userID, String username, UserColor color, Point position) {
		this.userID = userID;
		this.username = username;
		this.color = color;
		this.position = position;
	}

	/**
	 * Constructor to create a {@code UserModel} out of a {@link User} with
	 * adding a position
	 * 
	 * @param user a user
	 * @param position the position
	 */
	public UserModel(User user, Point position) {
		this.userID = user.getUserID();
		this.username = user.getUsername();
		this.color = user.getColor();
		this.position = position;
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

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * @return the ready
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * @param ready the ready to set
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
