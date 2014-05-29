package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.client.listener.ButtonListener;
import hrw.swenpr.bomberman.common.rfc.User;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
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
	
	private JButton chsLevel;
	private JButton chsTime;
	private JButton ready;
	private JButton logout;
	private ArrayList<User> users;
	private JTable userTable;
	private ButtonListener buttonListener;

	public Sidebar() {
		// create vertical layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		createView();
	}

	/**
	 * Build and add all view elements.
	 */
	private void createView() {
		// create listener for buttons
		buttonListener = new ButtonListener();
		
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
		userTable = new JTable(null, COLUMN_HEADS);
		userTable.setShowGrid(false);

		// add views
		add(userTable);
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

		String[][] content = new String[users.size()][];

		for (int i = 0; i < users.size(); i++) {
			// enlist position
			content[i][0] = Integer.toString(i + 1);
			// enlist username in first column
			content[i][1] = users.get(i).getUsername();
			// enlist userscore in second column
			content[i][2] = Integer.toString(users.get(i).getScore());
		}

		userTable = new JTable(content, COLUMN_HEADS);
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
}
