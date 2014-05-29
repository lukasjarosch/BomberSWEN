package hrw.swenpr.bomberman.client;

import java.util.ArrayList;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.User;

public class ClientModel extends BombermanBaseModel 
{
	
	/**
	 * Stores all available levels
	 */
	private ArrayList<Level> level;
	
	/**
	 * Sets a list of new level
	 * @param level List with available levels
	 */
	public void setLevels(ArrayList<Level> level)
	{
		this.level = level; 
	}
	
	/**
	 * Returns all available level
	 * @return {@link ArrayList} with available levels
	 */
	public ArrayList<Level> getAvailableLevel()
	{
		return level;
	}
	
	
	/**
	 * Removes a player if he is dead or left the game
	 * @param usr User which is deleted
	 */
	public void removePlayer(User usr)
	{
		// go through player list and check if a matching username exist
		for(UserModel user : users) {
			if(usr.getUserID() == user.getUserID())
				users.remove(usr);
		}
	}
}
