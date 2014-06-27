package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.client.listener.ButtonListener;
import hrw.swenpr.bomberman.common.rfc.User;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

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
	
	private static final String TIME_REMAINING_HEADER = "Verbleibende Zeit:\n";

	/**
	 * Action commands.
	 */
	public static final String LOGOUT = "logout";
	public static final String READY = "ready";
	public static final String CHOOSE_TIME = "chooseTime";
	public static final String CHOOSE_LEVEL = "chooseLevel";
	
	private MainClient client;
	private JButton chsLevel;
	private JButton ready;
	private JButton logout;
	private ArrayList<User> users;
	private JTable userTable;
	private JLabel level;
	private Font txtStyle;
	private ButtonListener buttonListener;
	
	/*
	 *  game timer components
	 */
	private JButton chsTime;
	private JTextField timeTextField;
	private long timeRemaining = 0; // remaining time in seconds
	private Timer timer = new Timer();
	private TimerTask timerTask = null;

	/**
	 * Create a sidebar.
	 * 
	 * @param client the instance of {@link MainClient}
	 */
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
		
		// time remaining
		timeTextField = new JTextField(TIME_REMAINING_HEADER + "Spiel noch nicht gestartet");
		
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
		userTable = new JTable(tmp, COLUMN_HEADS) {
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		userTable.setFont(txtStyle);
		userTable.setShowGrid(false);
		
		//Label displaying the chosen level
		level = new JLabel("Kein Level ausgewählt...");
		level.setFont(txtStyle);

		// add views
		add(timeTextField);
		add(new JScrollPane(userTable));
		add(level);
		add(chsLevel);
		add(chsTime);		
		add(ready);
		add(logout);
		setVisible(true);
		
		toogleAdmin(client.isAdmin());
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
				if (user.getUsername().equals(usr.getUsername()))
					user.setScore(usr.getScore());
			}
		}

		for (int i = 0; i < users.size(); i++) {
			// enlist position
			userTable.setValueAt(Integer.toString(i + 1), i, 0);
			// enlist username in first column
			userTable.setValueAt(users.get(i).getUsername(), i, 1);
			// enlist userscore in second column
			userTable.setValueAt(Integer.toString(users.get(i).getScore()), i, 2);
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
			if(usr.getUsername().equals(user.getUsername()))
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
	
	/**
	 * Set the timeRemaining text field.
	 * 
	 * @param minutes the minutes (5, 10, 15)
	 */
	public void setTime(int minutes) {
		timeTextField.setText(TIME_REMAINING_HEADER + minutes + " Minuten");
		// convert minutes to seconds
		timeRemaining = minutes * 60;
	}
	
	
	/**
	 * Start the timer of the game.
	 */
	public void startTimer() {
		// if old running instance exists -> cancel
		if(timerTask != null)
			timerTask.cancel();
		
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				timeRemaining--;
				
				int minutes = (int) timeRemaining / 60;
				int seconds = (int) timeRemaining - minutes * 60;
				
				timeTextField.setText(TIME_REMAINING_HEADER + minutes + ":" + seconds);
			}
		};
		timer.scheduleAtFixedRate(timerTask, 1000L, 1000L);
	}
	
	/**
	 * Stops the timer of the game.
	 */
	public void stopTimer() {
		if(timerTask != null)
			timerTask.cancel();
	}
}
