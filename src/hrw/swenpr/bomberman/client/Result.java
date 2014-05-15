package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.rfc.User;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;

public class Result extends JDialog
{
	private final String COLHEADS[] = {"Position", "Name", "Points"};
	private JTable table;
	private JLabel lbl;
	private MainClient mainClient;
	
	/**
	 * creates the dialog with a table and a message
	 * @param p coordinates of top left corner of mainWindwo
	 * @param r size of main window
	 * @param usr array with user-data
	 * @param type type of message, text changes with the type
	 */
	public Result(Point p, Rectangle r, User usr[], int type)
	{

	}
	
	public Result(Object object, Object object2, Object object3, int i,	MainClient mainClient)
	{
		this.mainClient = mainClient;
	}

	/**
	 * creates the result table
	 * @param usr user data
	 */
	private void makeTable(User usr[])
	{
				
	}
}
