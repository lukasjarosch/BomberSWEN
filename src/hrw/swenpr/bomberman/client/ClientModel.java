package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.BombEvent;
import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.UserPosition;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
	 * Flag that shows if player set a bomb on current panel
	 */
	private FieldType secondFieldType = FieldType.PLAIN_FIELD;
	
	/**
	 * Stores if player has super bomb
	 */
	private boolean haveSuperBomb = false;
	
	/**
	 * Stores if player has mega bomb
	 */
	private boolean haveMegaBomb = false;

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
	
	
	/**
	 * Tries to move a player to given position.
	 * 
	 * @param uPos containing the {@code userID} and the new {@code position}.
	 * @return true when move is allowed, false if move failed (player stays at old position)
	 */
	public synchronized boolean movePlayer(UserPosition uPos) {
		// if field not walkable return false
		if(!isWalkableField(getUserPosition(uPos.getUserID()), uPos.getPosition())) {
			return false;
		}
		
		for (UserModel user : users) {
			// if relevant user found
			if(user.getUserID() == uPos.getUserID()) {
				setField(user.getPosition(), secondFieldType);
				secondFieldType = FieldType.PLAIN_FIELD;			
				// set new field
				setField(uPos.getPosition(), convertToFieldType(uPos.getUserID()));
				// set new position in user array
				user.setPosition(uPos.getPosition());
			}
		}
		
		return true;
	}
	
	/**
	 * Adds a bomb to the model, that will trigger a {@link BombEvent} when exploding.
	 * 
	 * @param bomb
	 */
	public synchronized void setBomb(final Bomb bomb) {
		// set field to bomb
		switch (bomb.getBombType()) {
		case NORMAL_BOMB:
			setField(bomb.getPosition(), FieldType.NORMAL_BOMB);
			secondFieldType = FieldType.NORMAL_BOMB;
			break;
			
		case SUPER_BOMB:
			setField(bomb.getPosition(), FieldType.SUPER_BOMB);
			secondFieldType = FieldType.SUPER_BOMB;
			break;
			
		case MEGA_BOMB:
			setField(bomb.getPosition(), FieldType.MEGA_BOMB);
			secondFieldType = FieldType.MEGA_BOMB;
			break;

		default:
			break;
		}
		
		// create timer task
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// trigger event when bomb explodes
				onBombEvent(bomb.getUserID(), bomb.getBombType(), bomb.getPosition(), getExplosion(bomb));
			}
		}, bomb.getTime());
	}
	
	/**
	 * Returns if player have a super bomb
	 * @return value of flag
	 */
	public boolean isHaveSuperBomb() {
		return haveSuperBomb;
	}
	
	/**
	 * Set the flag if player can use a super bomb
	 * @param haveSuperBomb If player collected a super bomb true, else false
	 */
	public void setHaveSuperBomb(boolean haveSuperBomb) {
		this.haveSuperBomb = haveSuperBomb;
	}

	/**
	 * Returns if player have a super bomb
	 * @return value of flag
	 */
	public boolean isHaveMegaBomb() {
		return haveMegaBomb;
	}

	/**
	 * Set the flag if player can use a mega bomb
	 * @param haveSuperBomb If player collected a mega bomb true, else false
	 */
	public void setHaveMegaBomb(boolean haveMegaBomb) {
		this.haveMegaBomb = haveMegaBomb;
	}
}
