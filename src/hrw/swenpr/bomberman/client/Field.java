package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;
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

	private static final int NORMALBOMB = 0;
	private static final int SUPERBOMB = 1;
	private static final int MEGABOMB = 2;
	private static final int ITEMSUPERBOMB = 3;
	private static final int ITEMMEGABOMB = 4;
	private static final int PLAINFIELD = 5;
	private static final int INDESTRUCTIBLE = 6;
	private static final int DESTRUCTIBLE = 7;
	
	/**
	 * Stores all images for the pit
	 */
	private ImageIcon icons[];
	
	/**
	 * Stores a font which is used to display where the user is positioned
	 */
	private Font font;

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
		icons = new ImageIcon[9];
		loadIcons();
		font = new Font("Arial", Font.BOLD, 20);
		this.setFocusable(true);
	}

	/**
	 * Loads icons into array
	 */
	private void loadIcons() {
		String path = "icons" + File.separator;
		
		icons[NORMALBOMB] = new ImageIcon(path + "normal_bomb.png");
		icons[SUPERBOMB] = new ImageIcon(path + "super_bomb.png");
		icons[MEGABOMB] = new ImageIcon(path + "mega_bomb.png");
		icons[ITEMSUPERBOMB] = new ImageIcon(path + "item_super_bomb.png");
		icons[ITEMMEGABOMB] = new ImageIcon(path + "item_mega_bomb.png");
		icons[PLAINFIELD] = new ImageIcon(path + "plain_field.png");
		icons[INDESTRUCTIBLE] = new ImageIcon(path + "indestructible_block.png");
		icons[DESTRUCTIBLE] = new ImageIcon(path + "destructible_block.png");
		
	}

	/**
	 * changes position of players and bombs
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
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[PLAINFIELD]);
					break;
				case DESTRUCTIBLE_FIELD:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[DESTRUCTIBLE]);
					break;

				case INDESTRUCTUBLE_FIELD:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[INDESTRUCTIBLE]);
					break;

				case NORMAL_BOMB:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[NORMALBOMB]);
					break;

				case SUPER_BOMB:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[SUPERBOMB]);
					break;

				case MEGA_BOMB:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[MEGABOMB]);
					break;

				case ITEM_SUPER_BOMB:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[ITEMSUPERBOMB]);
					break;

				case ITEM_MEGA_BOMB:
					grid[i][j] = this.makeLabel("", new Dimension(sizex, sizey), icons[ITEMMEGABOMB]);
					break;

				case USER1:
					grid[i][j] = this.makeLabel("U0", new Dimension(sizex, sizey), icons[PLAINFIELD]);
					break;

				case USER2:
					grid[i][j] = this.makeLabel("U1", new Dimension(sizex, sizey), icons[PLAINFIELD]);
					break;

				case USER3:
					grid[i][j] = this.makeLabel("U2", new Dimension(sizex, sizey), icons[PLAINFIELD]);
					break;

				case USER4:
					grid[i][j] = this.makeLabel("U3", new Dimension(sizex, sizey), icons[PLAINFIELD]);
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
	 * Creates a new label with given parameters
	 * @param text Text which is displayed in the label
	 * @param size Size of the new label
	 * @param icon Icon which is displayed
	 * @return {@link JLabel}
	 */
	private JLabel makeLabel(String text, Dimension size, ImageIcon icon){
		//Rescale icon to label size
		icon.setImage(icon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_FAST));
		
		JLabel lbl = new JLabel();
		
		//Set label size
		lbl.setPreferredSize(size);
		//Set background color to white
		lbl.setBackground(Color.WHITE);
		//Set text color
		lbl.setForeground(Color.YELLOW);
		//Set horizontal alignment to center
		lbl.setHorizontalAlignment(JLabel.CENTER);
		//set horizontal text alignment to center
		lbl.setHorizontalTextPosition(JLabel.CENTER);
		//Set font style
		lbl.setFont(font);
		
		lbl.setOpaque(true);
		//if there is an icon set it
		if(icon != null)
			lbl.setIcon(icon);
		//if there is text display it
		if(!text.equals(""))
			lbl.setText(text);
		
		return lbl;
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
		
		//Delete player from old position
		Point oldPos = (Point) playerPosition.get(id);
		grid[oldPos.y][oldPos.x].setIcon(icons[PLAINFIELD]);
		grid[oldPos.y][oldPos.x].setText("");
		//See if there is an item on the new psoition
		if(bombPosition.containsKey(oldPos)){
			switch ((BombType)bombPosition.get(oldPos)) {
			case NORMAL_BOMB:
				grid[oldPos.y][oldPos.x].setIcon(icons[NORMALBOMB]);
				break;
			case SUPER_BOMB:
				grid[oldPos.y][oldPos.x].setIcon(icons[SUPERBOMB]);
				break;

			case MEGA_BOMB:
				grid[oldPos.y][oldPos.x].setIcon(icons[MEGABOMB]);
				break;

			}
		}
		
		//Add player to new position
		grid[newPos.y][newPos.x].setText("U" + id);
		playerPosition.remove(id);
		playerPosition.put(id, newPos);
	}
	
	/**
	 * Redraws a panel after a bomb exploded
	 * @param pos Position of the panel which is redrawn
	 */
	public void redrawPanel(Point pos){
		
		bombPosition.remove(pos);
		try {
			switch(MainClient.getInstance().getModel().getFieldType(pos)){
			case PLAIN_FIELD:
				grid[pos.y][pos.x].setBackground(Color.WHITE);
				grid[pos.y][pos.x].setIcon(icons[PLAINFIELD]);
				break;
			case ITEM_MEGA_BOMB:
				grid[pos.y][pos.x].setBackground(Color.WHITE);
				grid[pos.y][pos.x].setIcon(icons[ITEMMEGABOMB]);
				break;
			case ITEM_SUPER_BOMB:
				grid[pos.y][pos.x].setBackground(Color.WHITE);
				grid[pos.y][pos.x].setIcon(icons[ITEMSUPERBOMB]);
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
		//Determine which bomb is set and display a according sign
		switch (type) {
		case NORMAL_BOMB:
			grid[pos.y][pos.x].setIcon(icons[NORMALBOMB]);
			break;
		case SUPER_BOMB:
			grid[pos.y][pos.x].setIcon(icons[SUPERBOMB]);
			break;

		case MEGA_BOMB:
			grid[pos.y][pos.x].setIcon(icons[MEGABOMB]);
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
		grid[oldPos.y][oldPos.x].setIcon(icons[PLAINFIELD]);
		playerPosition.remove(id);
	}
}
