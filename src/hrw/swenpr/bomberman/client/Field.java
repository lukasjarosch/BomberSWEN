package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.UserPosition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;


/**
 * Provides the field and all correspondent methods 
 * @author Daniel Hofer
 *
 */
public class Field extends JPanel 
{
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
	
	public Field()
	{
		playerPosition = new Hashtable();
	}
	
	/**
	 * changes position of players and bombs
	 * @param members players with their position and color
	 * @param bombs position and kind of bomb
	 */
	public void drawField()
	{

	}
	
	/**
	 * draws new level
	 * @throws Exception 
	 */
	public void createNewField() throws Exception
	{
		mainClient = MainClient.getInstance();
		ClientModel model = mainClient.getModel();
		//get field size
		int x = model.getSize().x;
		int y = model.getSize().y;
		int sizey = this.getSize().height/x;
		int sizex = this.getSize().width/y;
		grid = new JLabel[x][y];
		this.setLayout(new GridLayout(x, y));
		
		//Going through the field 
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				System.out.println(i + ", " + j + ", " + model.getFieldType(i, j));
				//Determine the field type and set according symbols and
				//backgrounds
				switch (model.getFieldType(i, j)){
					//configure JLabels 
					case PLAIN_FIELD:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setOpaque(true);
						break;
					case DESTRUCTIBLE_FIELD:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.GRAY);
						grid[i][j].setOpaque(true);
						break;
						
					case INDESTRUCTUBLE_FIELD:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.BLACK);
						grid[i][j].setOpaque(true);
						break;
						
					case NORMAL_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("NB");
						grid[i][j].setOpaque(true);
						break;
						
					case SUPER_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("SB");
						grid[i][j].setOpaque(true);
						break;
						
					case MEGA_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("MB");
						grid[i][j].setOpaque(true);
						break;
						
					case ITEM_SUPER_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("ISB");
						grid[i][j].setOpaque(true);
						break;
						
					case ITEM_MEGA_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("IMB");
						grid[i][j].setOpaque(true);
						break;
						
					case USER1:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U1");
						grid[i][j].setOpaque(true);
						break;
						
					case USER2:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U2");
						grid[i][j].setOpaque(true);
						break;
						
					case USER3:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U3");
						grid[i][j].setOpaque(true);
						break;
						
					case USER4:
						grid[i][j] = new JLabel();
						grid[i][j].setPreferredSize(new Dimension(sizex, sizey));
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U4");
						grid[i][j].setOpaque(true);
						break;
					
					default:
						
						break;
				}
				//Add grid to panel
				this.add(grid[i][j], -1);
			}
		}
		
		for(UserModel tmpUsr: model.getUsers()) {
			playerPosition.put(tmpUsr.getUserID(), tmpUsr.getPosition());
		}
	}
	
	public void repositionUser(int id, Point newPos)
	{
		Point oldPos = ((UserPosition)playerPosition.get(id)).getPosition();
		grid[oldPos.x][oldPos.y].setText("");
		grid[newPos.x][newPos.y].setText("U" + id);
		playerPosition.remove(id);
		playerPosition.put(id, newPos);
	}
}
