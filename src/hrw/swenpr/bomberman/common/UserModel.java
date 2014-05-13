package hrw.swenpr.bomberman.common;

import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.User.UserColor;

import java.awt.Point;

public class UserModel {

	protected int userID;
	protected UserColor color;
	protected Point position;
	
	/**
	 * @param userID
	 * @param color
	 * @param position
	 */
	public UserModel(int userID, UserColor color, Point position) {
		this.userID = userID;
		this.color = color;
		this.position = position;
	}
	
	/**
	 * Constructor to create a {@code UserModel} out of a {@link User} with adding a position
	 * 
	 * @param user a user
	 * @param position the position
	 */
	public UserModel(User user, Point position) {
		this.userID = user.getUserID();
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
}