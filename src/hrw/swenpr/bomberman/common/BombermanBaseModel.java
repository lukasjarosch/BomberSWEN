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
	protected Point size = null;
	// user arraylist with ID, color and position for each user
	protected ArrayList<UserModel> users = new ArrayList<UserModel>();
	// undestroyable objects arraylist
	protected ArrayList<Point> indestructible = new ArrayList<Point>();
	// destroyable objects arraylist
	protected ArrayList<Destructible> destructible = new ArrayList<Destructible>();
	// builder for level file
	protected SAXBuilder builder = new SAXBuilder();
	// listener
	protected Vector<BombermanListener> subscribers = new Vector<BombermanListener>();
	// flag if level is loaded
	protected boolean levelLoaded = false;
	
	/**
	 * Specifies the field types.
	 * 
	 * @author Marco Egger
	 */
	public enum FieldType {
		PLAIN_FIELD,
		DESTROYABLE_FIELD,
		UNDESTROYABLE_FIELD,
		NORMAL_BOMB,
		SUPER_BOMB,
		MEGA_BOMB,
		ITEM_SUPER_BOMB,
		ITEM_MEGA_BOMB,
		USER1,
		USER2,
		USER3,
		USER4;
	}
	
	/**
	 * Creates an empty base model.
	 */
	public BombermanBaseModel() {}
	
	
	/**
	 * Create a model that holds all data of the game and provides the logic.
	 * 
	 * @param users ArrayList of user participating in the game
	 * @param level the file of the level to load/play
	 */
	public BombermanBaseModel(ArrayList<User> users, File level) {
		// load level
		loadLevel(level);
		
		// add all players
		for (User user: users) {
			addPlayer(user);
		}
	}
	
	
	/**
	 * Loads the level from file.
	 * 
	 * @param level the level
	 */
	public synchronized void loadLevel(File level) {
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
			List<Element> undestroy = root.getChild("undestroyable")
					.getChildren();
			for (Element elem : undestroy) {
				Point p = new Point(Integer.parseInt(elem.getChildText("x")),
						Integer.parseInt(elem.getChildText("y")));
				this.indestructible.add(p);
			}

			// read and store all destroyable fields
			List<Element> destroy = root.getChild("destroyable").getChildren();
			for (Element elem : destroy) {
				Destructible destruct = new Destructible(new Point(Integer.parseInt(elem.getChildText("x")), Integer.parseInt(elem.getChildText("y"))));
				destruct.setItem(Integer.parseInt(elem.getChildText("item")));
				
				this.destructible.add(destruct);
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

		// set the indestructible fields
		for (Point p : this.indestructible) {
			field[p.x][p.y] = FieldType.UNDESTROYABLE_FIELD;
		}
		// set the destructible fields
		for (Destructible dest : this.destructible) {
			field[dest.getPosition().x][dest.getPosition().y] = FieldType.DESTROYABLE_FIELD;
		}
		
		levelLoaded = true;
		
		// set all current participating users to their position
		placeUsersInCorners();
	}
	
	
	/**
	 * Adds an user to the model. Player gets automatically placed in the predefined corner.
	 * 
	 * @param user the user
	 */
	public synchronized void addPlayer(User user) {
		UserModel um = null;
		int x = size.x;
		int y = size.y;

		if (levelLoaded) {
			switch (user.getUserID()) {
			case 0:
				// convert User to UserModel with position
				um = new UserModel(user, new Point(0, 0));
				field[0][0] = FieldType.USER1;
				break;
			case 1:
				// convert User to UserModel with position
				um = new UserModel(user, new Point(x, 0));
				field[x][0] = FieldType.USER2;
				break;
			case 2:
				// convert User to UserModel with position
				um = new UserModel(user, new Point(x, y));
				field[x][y] = FieldType.USER3;
				break;
			case 3:
				// convert User to UserModel with position
				um = new UserModel(user, new Point(0, y));
				field[0][y] = FieldType.USER4;
				break;
			default:
				break;
			}
		} else {
			um = new UserModel(user, null);
		}
		
		// add user to model
		users.add(um);
	}
	
	
	/**
	 * @return the users
	 */
	public synchronized ArrayList<UserModel> getUsers() {
		return users;
	}
	
	
	/**
	 * Should be called when a level is loaded to place all user into their predefined corners.
	 */
	protected synchronized void placeUsersInCorners() {
		int x = size.x;
		int y = size.y;
		
		for(UserModel user: users) {
			switch (user.getUserID()) {
			case 0:
				user.setPosition(new Point(0, 0));
				field[0][0] = FieldType.USER1;
				break;
			case 1:
				user.setPosition(new Point(x, 0));
				field[x][0] = FieldType.USER2;
				break;
			case 2:
				user.setPosition(new Point(x, y));
				field[x][y] = FieldType.USER3;
				break;
			case 3:
				user.setPosition(new Point(0, y));
				field[0][y] = FieldType.USER4;
				break;
			default:
				break;
			}
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
				setField(uPos.getPosition(), convertToFieldType(uPos.getUserID()));
				// set new position in user array
				user.setPosition(uPos.getPosition());
				// trigger listener
				onBombermanEvent();
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
			break;
			
		case SUPER_BOMB:
			setField(bomb.getPosition(), FieldType.SUPER_BOMB);
			break;
			
		case MEGA_BOMB:
			setField(bomb.getPosition(), FieldType.MEGA_BOMB);
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
	 * Checks whether on the given position a player can be positioned.
	 * 
	 * @param pos the position
	 * @return true when it's possible
	 */
	protected synchronized boolean isWalkable(Point pos) {
		// if the field is not a plain field you can not walk on it
		try {
			
			if(getFieldType(pos) == FieldType.PLAIN_FIELD)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			// when exception occurs the value is out of the range of the pitch -> not walkable
			e.printStackTrace();
			return false;
		}
	}
	

	/**
	 * @return the size of the current level
	 */
	public synchronized Point getSize() {
		return size;
	}

	
	/**
	 * Determine the field type.
	 * 
	 * @param pos {@code Point} the position
	 * @return {@link FieldType} the fieldtype
	 * @throws Exception when trying to determine field type out of field size
	 */
	public synchronized FieldType getFieldType(Point pos) throws Exception {
		return field[pos.x][pos.y];
	}

	
	/**
	 * Determine the field type.
	 * 
	 * @param x int
	 * @param y int
	 * @return {@link FieldType} the fieldtype
	 * @throws Exception when trying to determine field type out of field size
	 */
	public synchronized FieldType getFieldType(int x, int y) throws Exception {
		return field[x][y];
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
	 * Converts a userID to a {@link FieldType}.
	 * @param userID
	 * @return the field type
	 */
	protected synchronized FieldType convertToFieldType(int userID) {
		switch (userID) {
		case 0:
			return FieldType.USER1;
		case 1:
			return FieldType.USER2;
		case 2:
			return FieldType.USER3;
		case 3:
			return FieldType.USER4;
		default:
			// this is just for satisfy the compiler. it is important that the userID is valid!
			return DEFAULT_FIELD;
		}
	}
	
	
	/**
	 * Converts a {@link FieldType} to an userID.
	 * 
	 * @param type the FieldType
	 * @return the userID or -1 if invalid FieldType
	 */
	protected synchronized int convertToUserID(FieldType type) {
		switch (type) {
		case USER1:
			return 0;
		case USER2:
			return 1;
		case USER3:
			return 2;
		case USER4:
			return 3;
		default:
			return -1;
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
	 * Calculates all affected fields of the explosion.
	 * 
	 * @param bomb the bomb with explosion
	 * @return {@code ArrayList<Point>} all fields that are affected by the explosion
	 */
	protected synchronized ArrayList<Point> getExplosion(Bomb bomb) {
		ArrayList<Point> affected = new ArrayList<Point>();
		Point pos = bomb.getPosition();
		
		switch (bomb.getBombType()) {
		case NORMAL_BOMB: // size of 3 field explosion
			affected.addAll(getFieldsFromPoint(pos, 3));
			break;
		case SUPER_BOMB: // size of 5 field explosion
			affected.addAll(getFieldsFromPoint(pos, 5));
			break;
		case MEGA_BOMB: // size of 7 field explosion
			affected.addAll(getFieldsFromPoint(pos, 7));
			break;
		default:
			break;
		}
		
		return affected;
	}
	
	
	/**
	 * Returns all fields around (left, up, right and down) the given field with a specific length.
	 * 
	 * @param pos {@code Point} the position of center field
	 * @param length the fields to go in every direction
	 * @return {@code ArrayList<Point>} the fields
	 */
	protected synchronized ArrayList<Point> getFieldsFromPoint(Point pos, int length) {
		ArrayList<Point> result = new ArrayList<Point>();
		Point p;
		
		for(int i = 1; i <= length; i++) {
			// left
			try {
				p = new Point(pos.x - i, pos.y);
				getFieldType(p);
				result.add(p);
			} catch (Exception e) {
				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not included in explosion
			}
			
			// up
			try {
				p = new Point(pos.x, pos.y - i);
				getFieldType(p);
				result.add(p);
			} catch (Exception e) {
				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not included in explosion
			}
			
			// right
			try {
				p = new Point(pos.x + i, pos.y);
				getFieldType(p);
				result.add(p);
			} catch (Exception e) {
				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not included in explosion
			}
			
			// down
			try {
				p = new Point(pos.x, pos.y + i);
				getFieldType(p);
				result.add(p);
			} catch (Exception e) {
				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not included in explosion
			}
		}
		
		return result;
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
	
	/**
	 * @return true when level is loaded
	 */
	public synchronized boolean isLevelLoaded() {
		return levelLoaded;
	}
	
	
	/**
	 * @param loaded the new levelLoaded state
	 */
	protected synchronized void setLevelLoaded(boolean loaded) {
		levelLoaded = loaded;
	}
	
	
	/**
	 * <p>Class for destructible objects. It holds the position and the special item.
	 * <p>item types:<br>
	 * • 0: no special item<br>
	 * • 1: super bomb<br>
	 * • 2: mega bomb<br>
	 * 
	 * @author Marco Egger
	 */
	public class Destructible {
		private Point position;
		private int item;
		
		/**
		 * Initialize a destructible object. Special item set to zero (no special item).
		 * 
		 * @param position the position
		 */
		public Destructible(Point position) {
			this.position = position;
			this.item = 0;
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
		 * @return the item
		 */
		public int getItem() {
			return item;
		}
		/**
		 * @param item the item to set
		 */
		public void setItem(int item) {
			this.item = item;
		}
	}
}
