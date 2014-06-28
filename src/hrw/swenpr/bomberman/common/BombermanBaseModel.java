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
 * The base model for client and server. It provides function that both sides
 * need.
 * 
 * @author Marco Egger
 */
public abstract class BombermanBaseModel {
	// the default field to inizialize
	protected final static FieldType DEFAULT_FIELD = FieldType.PLAIN_FIELD;
	// the default time of an explosion in milliseconds
	public static final long EXPLOSION_TIME = 700;

	// the logical pitch
	protected FieldType[][] field = null;
	// the size of the current level
	protected Point size = null;
	// user arraylist with ID, color and position for each user
	protected ArrayList<UserModel> users = new ArrayList<UserModel>();
	// indestructible objects arraylist
	protected ArrayList<Point> indestructible = new ArrayList<Point>();
	// destructible objects arraylist
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
		PLAIN_FIELD, DESTRUCTIBLE_FIELD, INDESTRUCTUBLE_FIELD, NORMAL_BOMB, SUPER_BOMB, MEGA_BOMB, ITEM_SUPER_BOMB, ITEM_MEGA_BOMB, USER1, USER2, USER3, USER4;
	}

	/**
	 * Creates an empty base model.
	 */
	public BombermanBaseModel() {
	}

	/**
	 * Create a model that holds all data of the game and provides the logic.
	 * 
	 * @param users
	 *            ArrayList of user participating in the game
	 * @param level
	 *            the file of the level to load/play
	 */
	public BombermanBaseModel(ArrayList<User> users, File level) {
		// load level
		loadLevel(level);

		// add all players
		for (User user : users) {
			addPlayer(user);
		}
	}

	/**
	 * Loads the level from file.
	 * 
	 * @param level
	 *            the level
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
			// - 1 because of the shifting from 1 to zero in an array
			size = new Point(x - 1, y - 1);

			// read and store all indestructible fields
			List<Element> indest = root.getChild("indestructible")
					.getChildren();
			for (Element elem : indest) {
				// read the position and reduce each value by 1 because an array
				// starts at zero and the level file starts with one
				Point p = new Point(
						Integer.parseInt(elem.getChildText("x")) - 1,
						Integer.parseInt(elem.getChildText("y")) - 1);
				this.indestructible.add(p);
			}

			// read and store all destructible fields
			List<Element> dest = root.getChild("destructible").getChildren();
			for (Element elem : dest) {
				// read the position and reduce each value by 1 because an array
				// starts at zero and the level file starts with one
				Destructible destruct = new Destructible(new Point(
						Integer.parseInt(elem.getChildText("x")) - 1,
						Integer.parseInt(elem.getChildText("y")) - 1));
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
			field[p.x][p.y] = FieldType.INDESTRUCTUBLE_FIELD;
		}
		// set the destructible fields
		for (Destructible dest : this.destructible) {
			field[dest.getPosition().x][dest.getPosition().y] = FieldType.DESTRUCTIBLE_FIELD;
		}

		levelLoaded = true;

		// set all current participating users to their position
		placeUsersInCorners();
	}

	/**
	 * Adds an user to the model. Player gets automatically placed in the
	 * predefined corner.
	 * 
	 * @param user
	 *            the user
	 */
	public synchronized void addPlayer(User user) {
		UserModel um = null;

		if (levelLoaded) {
			int x = size.x;
			int y = size.y;

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
	 * Returns corresponding User
	 * 
	 * @param id
	 *            User id
	 * @return Corresponding User
	 */
	public UserModel getUser(int id){
		for(UserModel user: users){
			if(user.getUserID() == id)
				return user;
		}
		return null;
	}

	/**
	 * Should be called when a level is loaded to place all user into their
	 * predefined corners.
	 */
	protected synchronized void placeUsersInCorners() {
		int x = size.x;
		int y = size.y;

		for (UserModel user : users) {
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
	 * @param uPos
	 *            containing the {@code userID} and the new {@code position}.
	 * @return true when move is allowed, false if move failed (player stays at
	 *         old position)
	 */
	public synchronized boolean movePlayer(UserPosition uPos) {
		// if field not walkable return false
		if (!isWalkableField(getUserPosition(uPos.getUserID()),
				uPos.getPosition())) {
			return false;
		}

		for (UserModel user : users) {
			// if relevant user found
			if (user.getUserID() == uPos.getUserID()) {
				// clear old field
				setField(user.getPosition(), FieldType.PLAIN_FIELD);
				// set new field
				setField(uPos.getPosition(),
						convertToFieldType(uPos.getUserID()));
				// set new position in user array
				user.setPosition(uPos.getPosition());
			}
		}

		return true;
	}

	/**
	 * Adds a bomb to the model, that will trigger a {@link BombEvent} when
	 * exploding.
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
				onBombEvent(bomb.getUserID(), bomb.getBombType(),
						bomb.getPosition(), getExplosion(bomb));
			}
		}, bomb.getTime());
	}

	/**
	 * Checks whether on the given position a player can be positioned.
	 * 
	 * @param pos
	 *            the position
	 * @return true when it's possible
	 */
	protected synchronized boolean isWalkableField(Point oldPos, Point newPos) {
		// catch exceptions when index out of bounds
		try {
			// first of all get point up, right, down and left of oldPos
			Point up = new Point(oldPos.x, oldPos.y - 1);
			Point right = new Point(oldPos.x + 1, oldPos.y);
			Point down = new Point(oldPos.x, oldPos.y + 1);
			Point left = new Point(oldPos.x - 1, oldPos.y);

			// check if newPos is equal one of these points
			if (!(newPos.equals(up) || newPos.equals(right)
					|| newPos.equals(down) || newPos.equals(left))) {
				return false;
			}

			// only reached when position is besides current position
			// then check if field is a plain field, all others are NOT walkable
			if (field[newPos.x][newPos.y] != FieldType.PLAIN_FIELD) {
				return false;
			}
		} catch (Exception e) {
			// when exception occurs the value is out of the range of the pitch
			// -> not walkable
			e.printStackTrace();
			return false;
		}

		// only reached when no return false -> field walkable
		return true;
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
	 * @param pos
	 *            {@code Point} the position
	 * @return {@link FieldType} the fieldtype
	 * @throws Exception
	 *             when trying to determine field type out of field size
	 */
	public synchronized FieldType getFieldType(Point pos) throws Exception {
		return field[pos.x][pos.y];
	}

	/**
	 * Determine the field type.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @return {@link FieldType} the fieldtype
	 * @throws Exception
	 *             when trying to determine field type out of field size
	 */
	public synchronized FieldType getFieldType(int x, int y) throws Exception {
		return field[x][y];
	}

	/**
	 * Returns the special item of a panel if there is one
	 * 
	 * @param pos
	 *            Position of the panel
	 * @return Kind of special item
	 */
	public FieldType getSpecialItem(Point pos) {
		int item = 0;
		for (Destructible des : destructible) {
			if (des.getPosition().equals(pos))
				item = des.getItem();
		}

		switch (item) {
		case 0:
			return FieldType.PLAIN_FIELD;
		case 1:
			return FieldType.ITEM_SUPER_BOMB;
		case 2:
			return FieldType.ITEM_MEGA_BOMB;
		default:
			break;
		}
		return FieldType.PLAIN_FIELD;
	}

	/**
	 * Sets the type of one field.
	 * 
	 * @param pos
	 *            the position
	 * @param type
	 *            the new type
	 */
	public synchronized void setField(Point pos, FieldType type) {
		field[pos.x][pos.y] = type;
	}

	/**
	 * Converts a userID to a {@link FieldType}.
	 * 
	 * @param userID
	 * @return the field type
	 */
	protected static synchronized FieldType convertToFieldType(int userID) {
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
			// this is just for satisfy the compiler. it is important that the
			// userID is valid!
			return DEFAULT_FIELD;
		}
	}

	/**
	 * Converts a {@link FieldType} to an userID.
	 * 
	 * @param type
	 *            the FieldType
	 * @return the userID or -1 if invalid FieldType
	 */
	public static synchronized int convertToUserID(FieldType type) {
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
	 * @param userID
	 *            the userID
	 * @return {@link Point} the position of the user. {@code null} when userID
	 *         does not exit
	 */
	public synchronized Point getUserPosition(int userID) {
		for (UserModel user : users) {
			// find relevant user with userID
			if (user.getUserID() == userID) {
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
	 * @param bomb
	 *            the bomb with explosion
	 * @return {@code ArrayList<Point>} all fields that are affected by the
	 *         explosion
	 */
	@SuppressWarnings("rawtypes")
	protected synchronized ArrayList[] getExplosion(Bomb bomb) {
		ArrayList[] affected = new ArrayList[4];
		Point pos = bomb.getPosition();

		switch (bomb.getBombType()) {
		case NORMAL_BOMB: // size of 3 field explosion
			affected = getFieldsFromPoint(pos, 3);
			break;
		case SUPER_BOMB: // size of 5 field explosion
			affected = getFieldsFromPoint(pos, 5);
			break;
		case MEGA_BOMB: // size of 7 field explosion
			affected = getFieldsFromPoint(pos, 7);
			break;
		default:
			break;
		}

		return affected;
	}

	/**
	 * Returns all fields around (left, up, right and down) the given field with
	 * a specific length.
	 * 
	 * @param pos
	 *            {@code Point} the position of center field
	 * @param length
	 *            the fields to go in every direction
	 * @return {@code ArrayList<Point>} the fields
	 */
	@SuppressWarnings("rawtypes")
	protected synchronized ArrayList[] getFieldsFromPoint(Point pos, int length) {
		ArrayList<Point> left = new ArrayList<Point>();
		ArrayList<Point> right = new ArrayList<Point>();
		ArrayList<Point> up = new ArrayList<Point>();
		ArrayList<Point> down = new ArrayList<Point>();
		ArrayList[] result = new ArrayList[4];

		Point p;

		for (int i = 1; i <= length; i++) {
			// left
			try {
				p = new Point(pos.x - i, pos.y);
				getFieldType(p);
				left.add(p);
			} catch (Exception e) {
//				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not
				// included in explosion
			}

			// up
			try {
				p = new Point(pos.x, pos.y - i);
				getFieldType(p);
				up.add(p);
			} catch (Exception e) {
//				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not
				// included in explosion
			}

			// right
			try {
				p = new Point(pos.x + i, pos.y);
				getFieldType(p);
				right.add(p);
			} catch (Exception e) {
//				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not
				// included in explosion
			}

			// down
			try {
				p = new Point(pos.x, pos.y + i);
				getFieldType(p);
				down.add(p);
			} catch (Exception e) {
//				e.printStackTrace();
				// when exception occurs the field is out of the pitch -> not
				// included in explosion
			}
		}

		result[0] = up;
		result[1] = right;
		result[2] = down;
		result[3] = left;

		return result;
	}
	
	/**
	 * Adds an bomberman listener to the model
	 * @param listener Listener which is added to the model
	 */
	public void setBombermanListener(BombermanListener listener){
		subscribers.add(listener);
	}

	/**
	 * Calling triggers all added listeners, when a bomb exploded.
	 * 
	 * @param userID
	 *            of the user who placed the bomb
	 * @param type
	 *            {@link BombType} the type of the bomb
	 * @param position
	 *            {@link Point} the position
	 * @param explosion
	 *            {@code ArrayList<Point>} all fields, the explosion reached
	 */
	@SuppressWarnings("rawtypes")
	protected synchronized void onBombEvent(int userID, BombType type,
			Point position, ArrayList[] explosion) {
		for (BombermanListener listener : subscribers) {
			listener.bombExplode(new BombEvent(this, userID, type, position,
					explosion));
		}
	}

	/**
	 * @return true when level is loaded
	 */
	public synchronized boolean isLevelLoaded() {
		return levelLoaded;
	}

	/**
	 * @param loaded
	 *            the new levelLoaded state
	 */
	protected synchronized void setLevelLoaded(boolean loaded) {
		levelLoaded = loaded;
	}

	/**
	 * <p>
	 * Class for destructible objects. It holds the position and the special
	 * item.
	 * <p>
	 * item types:<br>
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
		 * Initialize a destructible object. Special item set to zero (no
		 * special item).
		 * 
		 * @param position
		 *            the position
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
		 * @param position
		 *            the position to set
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
		 * @param item
		 *            the item to set
		 */
		public void setItem(int item) {
			this.item = item;
		}
	}
}
