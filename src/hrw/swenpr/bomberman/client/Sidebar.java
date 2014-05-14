package hrw.swenpr.bomberman.client;

import java.awt.Color;

import hrw.swenpr.bomberman.client.listener.ButtonListener;
import hrw.swenpr.bomberman.common.rfc.User;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JToolBar;

public class Sidebar extends JToolBar 
{

	private static final long serialVersionUID = 3L;
	
	private final String COLHEADS[] = {"Position", "Name", "Points"};
	private JButton chsLevel;
	private JButton chsTime;
	private JButton ready;
	private JButton logout;
	private JTable tbl_usr;  
	private ButtonListener buttonListener;
	private MainClient mainClient;
	
	
	public Sidebar(MainClient mainClient)
	{
		super();
		this.makeToolbar();
		this.mainClient = mainClient;
	}
	
	/**
	 * builds the toolbar
	 */
	private void makeToolbar()
	{
		this.setFloatable(false);		
		String[][] tmp = {{"1","2","3"}, {"1","2","3"}};
		buttonListener = new ButtonListener(mainClient);
		chsLevel = new JButton("Choose Level");
		chsTime = new JButton("Choose time");		
		ready = new JButton("ready");
		tbl_usr = new JTable(tmp, COLHEADS);
		tbl_usr.setShowGrid(false);
		
		this.add(tbl_usr);
		this.add(ready);
		this.setVisible(true);
	}
	
	/**
	 * updates user table with new data
	 * @param usr array with all usernames and their score
	 */
	private void updateTable(User[] usr)
	{
		String[][] data = null;
		data = new String[usr.length][1];
		
		for(int i = 0, j = 0; i < usr.length; i++, j++)
		{
			//enlist position
			data[i][0] = Integer.toString(i + 1);
			//enlist username in first column
			data[i][1] = usr[i].getUsername();
			//enlist userscore in second column
			data[i][2] = Integer.toString(usr[i].getScore());
		}
		
		tbl_usr = new JTable(data, COLHEADS);
	}
	
	
	/**
	 * displays or deletes buttons for admin
	 */
	public void toogleMaster()
	{
		
	}
}
