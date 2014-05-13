package hrw.swenpr.bomberman.common;

import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserPosition;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public abstract class BombermanBaseModel {
	protected final FieldType DEFAULT_FIELD = FieldType.PLAIN_FIELD; 
	protected FieldType[][] field = null;
	protected ArrayList<UserModel> users = new ArrayList<UserModel>();
	protected ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	protected ArrayList<Point> undestroy = new ArrayList<Point>();
	protected ArrayList<Point> destroy = new ArrayList<Point>();
	protected SAXBuilder builder = new SAXBuilder();
	
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
	 * Create a model that holds all data of the game.
	 * 
	 * @param field
	 * @param users
	 * @param bombs
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


	/**
	 * Determine the field type.
	 * 
	 * @param pos the position
	 * @return {@link FieldType} the fieldtype
	 * @throws IndexOutOfBoundsException when trying to determine field type out of field size
	 */
	protected synchronized FieldType getField(Point pos) throws IndexOutOfBoundsException {
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
			if(user.getUserID() == userID) {
				return user.getPosition();
			}
		}
		return null;
	}

	
	/**
	 * Adds the bomb to the model.
	 * 
	 * @param bomb
	 */
	public synchronized void setBomb(Bomb bomb) {
		bombs.add(bomb);
	}
}
