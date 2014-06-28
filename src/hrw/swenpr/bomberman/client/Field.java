package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

/**
 * Provides the field and all correspondent methods
 * 
 * @author Daniel Hofer
 * 
 */
public class Field extends JPanel {
	private static final long serialVersionUID = 1L;

	private Image player[];
	private Image field;
	private Image Stone;
	private Image bomb[];
	private Image indestructible;

	/**
	 * Stores the single panels which represent the pit altogether
	 */
	private JLabel grid[][];

	/**
	 * Stores reference to MainWindow
	 */
	private MainClient mainClient;

	/**
	 * Stores the position of all player
	 */
	private Hashtable playerPosition;
	
	/**
	 * Stores the position of all bombs
	 */
	private Hashtable bombPosition;

	public Field() {
		super();
		playerPosition = new Hashtable();
		bombPosition = new Hashtable();
		this.setFocusable(true);
	}

	/**
	 * changes position of players and bombs
	 * 
	 * @param members
	 *            players with their position and color
	 * @param bombs
	 *            position and kind of bomb
	 */
	public void drawField() {

	}

	/**
	 * draws new level
	 * 
	 * @throws Exception
	 */
	public void createNewField() throws Exception {
		mainClient = MainClient.getInstance();
		ClientModel model = mainClient.getModel();
		// get field size
		int x = model.getSize().x + 1;
		int y = model.getSize().y + 1;
		
		//Calculate size of each panels
		int sizey = this.getSize().height / x;
		int sizex = this.getSize().width / y;
		grid = new JLabel[x][y];
		this.setLayout(new GridLayout(x, y));
		// Going through the field
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				// Determine the field type and set according symbols and
				// backgrounds
				switch (model.getFieldType(j, i)) {
				// configure JLabels
				case PLAIN_FIELD:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;
				case DESTRUCTIBLE_FIELD:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.GRAY);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case INDESTRUCTUBLE_FIELD:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.BLACK);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case NORMAL_BOMB:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case SUPER_BOMB:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case MEGA_BOMB:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case ITEM_SUPER_BOMB:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case ITEM_MEGA_BOMB:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setOpaque(true);
					break;

				case USER1:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setText("U0");
					grid[i][j].setOpaque(true);
					break;

				case USER2:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setText("U1");
					grid[i][j].setOpaque(true);
					break;

				case USER3:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setText("U2");
					grid[i][j].setOpaque(true);
					break;

				case USER4:
					grid[i][j] = new JLabel();
					grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
					grid[i][j].setBackground(Color.WHITE);
					grid[i][j].setHorizontalAlignment(JLabel.CENTER);
					grid[i][j].setText("U3");
					grid[i][j].setOpaque(true);
					break;

				default:

					break;
				}
				// Add grid to panel
				this.add(grid[i][j], -1);
			}
		}

		for (UserModel tmpUsr : model.getUsers()) {
			playerPosition.put(tmpUsr.getUserID(), tmpUsr.getPosition());
		}
	}

	/**
	 * Moves a player figure on the field
	 * 
	 * @param id
	 *            Player ID of moved figure
	 * @param newPos
	 *            New position of player
	 */
	public void repositionUser(int id, Point newPos) {
		//Saving sign of the new panel
		String field = "";
		field = grid[newPos.y][newPos.x].getText();
		if(!field.equals("")){
			field += ", ";
		}
		//Delete player from old position
		Point oldPos = (Point) playerPosition.get(id);
		grid[oldPos.y][oldPos.x].setText("");
		//See if there is an item on the new psoition
		if(bombPosition.containsKey(oldPos)){
			switch ((BombType)bombPosition.get(oldPos)) {
			case NORMAL_BOMB:
				grid[oldPos.y][oldPos.x].setText("NB");
				break;
			case SUPER_BOMB:
				grid[oldPos.y][oldPos.x].setText("SB");
				break;

			case MEGA_BOMB:
				grid[oldPos.y][oldPos.x].setText("MB");
				break;

			}
		}
		
		//Add player to new position
		grid[newPos.y][newPos.x].setText(field + "U" + id);
		playerPosition.remove(id);
		playerPosition.put(id, newPos);
	}
	
	/**
	 * Redraws a panel after a bomb exploded
	 * @param pos Position of the panel which is redrawn
	 */
	public void redrawPanel(Point pos){
		
		try {
			switch(MainClient.getInstance().getModel().getFieldType(pos)){
			case PLAIN_FIELD:
				grid[pos.y][pos.x].setBackground(Color.WHITE);
				grid[pos.y][pos.x].setText("");
				break;
			case ITEM_MEGA_BOMB:
				grid[pos.y][pos.x].setBackground(Color.WHITE);
				grid[pos.y][pos.x].setText("IMB");
				break;
			case ITEM_SUPER_BOMB:
				grid[pos.y][pos.x].setBackground(Color.WHITE);
				grid[pos.y][pos.x].setText("ISB");
				break;
			default:
				break;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Places a new bomb on the field
	 * 
	 * @param pos
	 *            Position of the bomb
	 */
	public void setBomb(Point pos, BombType type) {
		//Saving sign before placing the bomb
		String field = "";
		field = grid[pos.y][pos.x].getText();
		if(!field.equals("")){
			field += ", ";
		}
		grid[pos.y][pos.x].setText("");
		//Determine which bomb is played and display a according sign
		switch (type) {
		case NORMAL_BOMB:
			grid[pos.y][pos.x].setText(field + "NB");
			break;
		case SUPER_BOMB:
			grid[pos.y][pos.x].setText(field + "SB");
			break;

		case MEGA_BOMB:
			grid[pos.y][pos.x].setText(field + "MB");
			break;

		}
		//Save bombs position
		bombPosition.put(pos, type);
	}
	
	/**
	 * deletes Player
	 * @param id Player ID
	 */
	public void deletePlayer(int id) {
		//Getting the players position and remove it from the list
		Point oldPos = (Point) playerPosition.get(id);
		grid[oldPos.y][oldPos.x].setText("");
		playerPosition.remove(id);
	}
}
