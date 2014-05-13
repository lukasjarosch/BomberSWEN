package hrw.swenpr.bomberman.common;

import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserPosition;
import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * The base model for client and server. It provides function that both sides need.
 * 
 * @author Marco Egger
 */
public abstract class BombermanBaseModel {
	// the default field to inizialize
	protected final FieldType DEFAULT_FIELD = FieldType.PLAIN_FIELD;
	// the default time of an explosion in milliseconds
	public static final long EXPLOSION_TIME = 700;
	
	// the logical pitch
	protected FieldType[][] field = null;
	// the size of the current level
	protected Point size;
	// user arraylist with ID, color and position for each user
	protected ArrayList<UserModel> users = new ArrayList<UserModel>();
	// bomb arraylist
	protected ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	// undestroyable objects arraylist
	protected ArrayList<Point> undestroy = new ArrayList<Point>();
	// destroyable objects arraylist
	protected ArrayList<Point> destroy = new ArrayList<Point>();
	// builder for level file
	protected SAXBuilder builder = new SAXBuilder();
	// listener
	protected Vector<BombermanListener> subscribers = new Vector<BombermanListener>();
	
	/**
	 * Specifies the field types.
	 * 
	 * @author Marco Egger
	 */
	public enum FieldType {
		PLAIN_FIELD,
		DESTROYABLE_FIELD,
		UNDESTROYABLE_FIELD,
		BOMB,
		USER1,
		USER2,
		USER3,
		USER4;
	}
	
	
	/**
	 * Create a model that holds all data of the game and provides the logic.
	 * 
	 * @param users ArrayList of user participating in the game
	 * @param level the file of the level to load/play
	 */
	public BombermanBaseModel(ArrayList<User> users, File level) {
		int x = 0;
		int y = 0; 
		
		// read in level
		try {
			Document doc = (Document) builder.build(level);
			Element root = doc.getRootElement();
			
			// determine size
			x = Integer.parseInt(root.getChild("size").getChildText("x"));
			y = Integer.parseInt(root.getChild("size").getChildText("y"));
			
			// store on model
			size = new Point(x, y);
			
			// read and store all undestroyable fields
			List<Element> undestroy = root.getChild("undestroyable").getChildren();
			for (Element elem : undestroy) {
				Point p = new Point(Integer.parseInt(elem.getChildText("x")), Integer.parseInt(elem.getChildText("y")));
				this.undestroy.add(p);
			}
			
			// read and store all destroyable fields
			List<Element> destroy = root.getChild("destroyable").getChildren();
			for (Element elem : destroy) {
				Point p = new Point(Integer.parseInt(elem.getChildText("x")), Integer.parseInt(elem.getChildText("y")));
				this.destroy.add(p);
			}
			
		} catch (JDOMException | IOException e) {
			// when error on level file occurred quit software
			e.printStackTrace();
			System.exit(0);
		}
		
		
		// initialize array with given range
		field = new FieldType[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				// set default type to every field
				field[i][j] = DEFAULT_FIELD;
			}
		}
		
		// set the undestroyable fields
		for (Point p : this.undestroy) {
			field[p.x][p.y] = FieldType.UNDESTROYABLE_FIELD;
		}
		// set the destroyable fields
		for (Point p : this.destroy) {
			field[p.x][p.y] = FieldType.DESTROYABLE_FIELD;
		}
		
		// set users into corners
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			UserModel user = null;
			
			// each user has his predefined position
			switch (u.getUserID()) {
			case 0:
				// convert User to UserModel with position
				user = new UserModel(u, new Point(0, 0));
				field[0][0] = FieldType.USER1;
				break;
			case 1:
				// convert User to UserModel with position
				user = new UserModel(u, new Point(x, 0));
				field[x][0] = FieldType.USER2;
				break;
			case 2:
				// convert User to UserModel with position
				user = new UserModel(u, new Point(x, y));
				field[x][y] = FieldType.USER3;
				break;
			case 3:
				// convert User to UserModel with position
				user = new UserModel(u, new Point(0, y));
				field[0][y] = FieldType.USER4;
				break;
			default:
				break;
			}
			
			// finally add the new user to array
			this.users.add(user);
		}
	}
	
	/**
	 * Tries to move a player to given position.
	 * 
	 * @param uPos containing the {@code userID} and the new {@code position}.
	 * @return true when move is allowed, false if move failed (player stays at old position)
	 */
	public synchronized boolean movePlayer(UserPosition uPos) {
		// if field not walkable return false
		if(!isWalkable(uPos.getPosition())) {
			return false;
		}
		
		for (UserModel user : users) {
			// if relevant user found
			if(user.getUserID() == uPos.getUserID()) {
				// clear old field
				setField(user.getPosition(), FieldType.PLAIN_FIELD);
				// set new field
				setField(uPos.getPosition(), uPos.getUserID());
				// set new position in user array
				user.setPosition(uPos.getPosition());
				// trigger listener
				onBombermanEvent();
			}
		}
		
		return true;
	}
	
	/**
	 * Checks whether on the given position a player can be positioned.
	 * 
	 * @param pos the position
	 * @return true when it's possible
	 */
	protected synchronized boolean isWalkable(Point pos) {
		// if the field is not a plain field you can not walk on it
		if(getField(pos) == FieldType.PLAIN_FIELD)
			return true;
		else
			return false;
	}

	
	public synchronized Point getSize() {
		return size;
	}

	/**
	 * Determine the field type.
	 * 
	 * @param pos the position
	 * @return {@link FieldType} the fieldtype
	 * @throws IndexOutOfBoundsException when trying to determine field type out of field size
	 */
	public synchronized FieldType getField(Point pos) throws IndexOutOfBoundsException {
		return field[pos.x][pos.y];
	}
	
	/**
	 * Sets the type of one field.
	 * 
	 * @param pos the position
	 * @param type the new type
	 */
	protected synchronized void setField(Point pos, FieldType type) {
		field[pos.x][pos.y] = type;
	}
	
	/**
	 * Sets the type of one field according to userID.
	 * 
	 * @param pos the position
	 * @param userID the userID
	 */
	protected synchronized void setField(Point pos, int userID) {
		// userID "converted" to FieldType with switch and then just calling setField(Point, FieldType)
		switch (userID) {
		case 0:
			setField(pos, FieldType.USER1);
			break;
		case 1:
			setField(pos, FieldType.USER2);
			break;
		case 2:
			setField(pos, FieldType.USER3);
			break;
		case 3:
			setField(pos, FieldType.USER4);
			break;
		default:
			break;
		}
	}


	/**
	 * Determine the position of a user using {@code userID}.
	 * 
	 * @param userID the userID
	 * @return {@link Point} the position of the user. {@code null} when userID does not exit
	 */
	public synchronized Point getUserPosition(int userID) {
		for (UserModel user: users) {
			// find relevant user with userID
			if(user.getUserID() == userID) {
				// return the position of this user
				return user.getPosition();
			}
		}
		
		// this is only reached when no matching userID's where found
		return null;
	}
	
	
	/**
	 * Calculates all affected fields of the explosion. It may triggers {@link UserDeadEvent} and/or {@link BombermanEvent}.
	 * 
	 * @param bomb the bomb with explosion
	 * @return {@code ArrayList<Point>} all fields that are affected by the explosion
	 */
	protected synchronized ArrayList<Point> getExplosion(Bomb bomb) {
		ArrayList<Point> affected = new ArrayList<Point>();
		
		switch (bomb.getBombType()) {
		case NORMAL_BOMB: // size of 3 field explosion
			
			break;
		case SUPER_BOMB: // size of 5 field explosion
			
			break;
		case MEGA_BOMB: // size of 7 field explosion
			
			break;
		default:
			break;
		}
		
		return affected;
	}

	
	/**
	 * Adds a bomb to the model.
	 * 
	 * @param bomb
	 */
	public synchronized void setBomb(final Bomb bomb) {
		bombs.add(bomb);
		
		// create timer task
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				onBombEvent(bomb.getUserID(), bomb.getBombType(), bomb.getPosition(), getExplosion(bomb));
			}
		}, bomb.getTime());
	}
	
	
	/**
	 * Calling triggers all added listeners, when the model is changed. E.g. a player moved or objects were destroyed.
	 */
	protected synchronized void onBombermanEvent() {
		for (BombermanListener listener: subscribers) {
			listener.modelChanged(new BombermanEvent(this));
		}
	}
	
	/**
	 * Calling triggers all added listeners, when a bomb exploded.
	 * 
	 * @param userID of the user who placed the bomb
	 * @param type {@link BombType} the type of the bomb
	 * @param position {@link Point} the position 
	 * @param explosion {@code ArrayList<Point>} all fields, the explosion reached
	 */
	protected synchronized void onBombEvent(int userID, BombType type, Point position, ArrayList<Point> explosion) {
		for (BombermanListener listener: subscribers) {
			listener.bombExplode(new BombEvent(this, userID, type, position, explosion));
		}
	}
}
