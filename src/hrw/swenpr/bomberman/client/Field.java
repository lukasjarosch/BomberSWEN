package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.User;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

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
	private int grid[][];
	
	
	public Field()
	{
		
	}
	
	/**
	 * changes position of players and bombs
	 * @param members players with their position and color
	 * @param bombs position and kind of bomb
	 */
	public void drawField(User members[], Bomb bombs[], File level)
	{
		
	}
	
	/**
	 * draws new level
	 * @param members players with their position and color
	 * @param bombs position and kind of bomb
	 * @param level new selected level
	 */
	public void createnewfield(User members[], Bomb bombs[], Level level)
	{

	}
	
	/**
	 * Paints the background picture
	 */
	protected void paintComponent(Graphics g) 
	{

	}
}