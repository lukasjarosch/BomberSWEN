package hrw.swenpr.bomberman.client;

import java.util.ArrayList;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.User;

/**
 * Stores all the information about the game the client needs
 * 
 * @author Daniel Hofer
 */
public class ClientModel extends BombermanBaseModel {

	/**
	 * Stores all available levels
	 */
	private ArrayList<Level> level;

	/**
	 * Stores the name of the chosen level
	 */
	private String filename;

	/**
	 * Sets a list of new level
	 * 
	 * @param level
	 *            List with available levels
	 */
	public void setLevels(ArrayList<Level> level) {
		this.level = level;
	}

	/**
	 * Returns all available level
	 * 
	 * @return {@link ArrayList} with available levels
	 */
	public ArrayList<Level> getAvailableLevel() {
		return level;
	}

	/**
	 * Removes a player if he is dead or left the game
	 * 
	 * @param usr
	 *            User which is deleted
	 * @deprecated
	 */
	public void removePlayer(UserModel usr) {
		// go through player list and check if a matching username exist
		for (UserModel user : users) {
			if (usr.getUserID() == user.getUserID())
				users.remove(usr);
		}
	}

	/**
	 * Deletes corresponding user
	 */
	public void deleteUserByID(int id) {

		for (UserModel user : users) {
			if (id == user.getUserID())
				users.remove(user);
		}
	}

	/**
	 * Sets level name
	 * 
	 * @param filename
	 *            Level name
	 */
	public void setLevelName(String filename) {
		this.filename = filename;

	}

	/**
	 * Returns the level name
	 * 
	 * @return Level name
	 */
	public String getLevelName() {
		return filename;
	}
}
