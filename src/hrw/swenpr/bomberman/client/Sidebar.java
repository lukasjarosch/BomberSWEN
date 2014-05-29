package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.client.listener.ButtonListener;
import hrw.swenpr.bomberman.common.rfc.User;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * <p>Provides information to the user e.g. users and their scores. Also for the admin selection for the level and time.
 * 
 * <p>Extends {@link JPanel}.
 * 
 * @author Daniel Hofer
 * @author Marco Egger
 */
public class Sidebar extends JPanel {

	private static final long serialVersionUID = 3L;

	private static final String COLUMN_HEADS[] = { "Position", "Name", "Points" };

	/**
	 * Action commands.
	 */
	public static final String LOGOUT = "logout";
	public static final String READY = "ready";
	public static final String CHOOSE_TIME = "chooseTime";
	public static final String CHOOSE_LEVEL = "chooseLevel";
	
	private MainClient client;
	private JButton chsLevel;
	private JButton chsTime;
	private JButton ready;
	private JButton logout;
	private ArrayList<User> users;
	private JTable userTable;
	private JLabel level;
	private Font txtStyle;
	private ButtonListener buttonListener;

	public Sidebar(MainClient client) {
		this.client = client;
		
		// create vertical layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		createView();
	}

	/**
	 * Build and add all view elements.
	 */
	private void createView() {
		// create listener for buttons
		buttonListener = new ButtonListener(client);
		
		users = new ArrayList<User>();
		
		//create font
		txtStyle = new Font("Arial", Font.PLAIN, 15);
		
		// level
		chsLevel = new JButton("Level auswählen");
		chsLevel.setActionCommand(CHOOSE_LEVEL);
		chsLevel.addActionListener(buttonListener);
		
		// time
		chsTime = new JButton("Spielzeit auswählen");
		chsTime.setActionCommand(CHOOSE_TIME);
		chsTime.addActionListener(buttonListener);
		
		// ready
		ready = new JButton("bereit");
		ready.setActionCommand(READY);
		ready.addActionListener(buttonListener);
		
		// logout
		logout = new JButton("verlassen");
		logout.setActionCommand(LOGOUT);
		logout.addActionListener(buttonListener);
		
		// user table
		String[][] tmp = { {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""} };
		userTable = new JTable(tmp, COLUMN_HEADS);
		userTable.setFont(txtStyle);
		userTable.setShowGrid(false);
		
		//Label displaying the chosen level
		level = new JLabel("Kein Level ausgewählt...");
		level.setFont(txtStyle);

		// add views
		add(new JScrollPane(userTable));
		add(level);
		add(chsLevel);
		add(chsTime);
		add(ready);
		add(logout);
		setVisible(true);
	}

	/**
	 * Updates user table with new data.
	 * 
	 * @param usr an user
	 */
	public void updateTable(User usr) {
		// when user is not already in table
		if (!isUserInTable(usr)) {
			users.add(usr);
		} else {
			// when user exist just update score
			for (User user : users) {
				if (user.getUsername() == usr.getUsername()) {
					user.setScore(usr.getScore());
				}
			}
		}

		for (int i = 0; i < users.size(); i++) {
			// enlist position
			userTable.setValueAt(Integer.toString(i + 1), i, 0);
			// enlist username in first column
			userTable.setValueAt(users.get(i).getUsername(), i, 1);
			// enlist userscore in second column
			userTable.setValueAt(users.get(i).getScore(), i, 2);
		}

		userTable.repaint();
	}
	
	
	/**
	 * Checks if user already exists in table.
	 * 
	 * @param user the user
	 * @return true when user already exists
	 */
	private boolean isUserInTable(User user) {
		boolean result = false;
		
		// go through player list and check if a matching username exist
		for(User usr : users) {
			if(usr.getUsername() == user.getUsername())
				result = true;
		}
		
		return result;
	}

	/**
	 * Toggles view between admin and normal user.
	 * 
	 * @param admin indicates if player is admin or not
	 */
	public void toogleAdmin(boolean admin) {
		if(admin) {
			chsLevel.setVisible(true);
			chsTime.setVisible(true);
		}
		else {
			chsLevel.setVisible(false);
			chsTime.setVisible(false);
		}
	}
	
	/**
	 * Updates the name of the displayed level
	 * @param name New level name
	 */
	public void updateLevel(String name)
	{
		level.setText("Levelname: " + name);
	}
}
