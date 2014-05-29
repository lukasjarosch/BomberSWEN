package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.User;

public class ClientModel extends BombermanBaseModel 
{
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
