package hrw.swenpr.bomberman.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;


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
	private JLabel grid[][];
	private MainClient mainClient;
	
	public Field()
	{
		
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
		grid = new JLabel[x][y];
		this.setLayout(new GridLayout(x, y));
		
		//Going through the field 
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				
				//Determine the field type and set according symbols and
				//backgrounds
				switch (model.getFieldType(x, y)){
					case PLAIN_FIELD:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						break;
					case DESTRUCTIBLE_FIELD:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.GRAY);
						break;
						
					case INDESTRUCTUBLE_FIELD:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.BLACK);
						break;
						
					case NORMAL_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("NB");
						break;
						
					case SUPER_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("SB");
						break;
						
					case MEGA_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("MB");
						break;
						
					case ITEM_SUPER_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("ISB");
						break;
						
					case ITEM_MEGA_BOMB:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("IMB");
						break;
						
					case USER1:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U1");
						break;
						
					case USER2:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U2");
						break;
						
					case USER3:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U2");
						break;
						
					case USER4:
						grid[i][j] = new JLabel();
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setText("U3");
						break;
					
					default:
						
						break;
				}
				//Add grid to panel
				this.add(grid[i][j]);
			}
		}
		//repaint field
		this.repaint();
	}
	
	/**
	 * Paints the background picture
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
}
