package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.client.listener.GameKeyListener;
import hrw.swenpr.bomberman.client.listener.GameListener;
import hrw.swenpr.bomberman.common.BombermanBaseModel.FieldType;
import hrw.swenpr.bomberman.common.ClientConnection;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.GameStart;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.LevelSelection;
import hrw.swenpr.bomberman.common.rfc.Login;
import hrw.swenpr.bomberman.common.rfc.RoundFinished;
import hrw.swenpr.bomberman.common.rfc.RoundStart;
import hrw.swenpr.bomberman.common.rfc.TimeSelection;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;
import hrw.swenpr.bomberman.common.rfc.User.UserColor;
import hrw.swenpr.bomberman.common.rfc.UserDead;
import hrw.swenpr.bomberman.common.rfc.UserPosition;
import hrw.swenpr.bomberman.common.rfc.UserRemove;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * <p>
 * Main class containing {@code main()} function for starting client.
 * </p>
 * 
 * <p>
 * Extends {@link JFrame}.
 * </p>
 * 
 * @author Marco Egger
 * @author Daniel Hofer
 */
public class MainClient extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_PORT = 6969;

	private static MainClient instance;

	private Communication com;
	private Sidebar sidebar;
	private Field field;
	private ClientModel model;

	private boolean isAdmin = false;
	private int userID;
	private boolean isDead = false;

	private Socket socket;

	/**
	 * Starting function.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainClient.getInstance();
	}

	public static MainClient getInstance() {
		if (instance == null)
			instance = new MainClient();

		return instance;
	}

	/**
	 * Default constructor for main window.
	 */
	public MainClient() {
		model = new ClientModel();

		// setting up JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bomberman");

		// make window full-screen
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// position and show window
		setLocationRelativeTo(null);

		sidebar = new Sidebar(this);
		field = new Field();

		add(sidebar, BorderLayout.EAST);
		add(field, BorderLayout.CENTER);

		setListener();

		// show login dialog
		this.showLogin();

		setVisible(true);
		this.setFocusable(true);
		// this.requestFocus();
	}

	/**
	 * @return the model
	 */
	public ClientModel getModel() {
		return model;
	}

	private void setListener() {
		this.addKeyListener(new GameKeyListener(this));
		model.setBombermanListener(new GameListener());
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				// send remove message when window closed
				com.sendMessage(new UserRemove(userID));
			}
		});
	}

	/**
	 * shows LoginWindow and sends login-request with entered data to server
	 */
	private void showLogin() {
		// create textfield and color array
		JTextField ipAddress = new JTextField("localhost");
		JTextField name = new JTextField(20);
		UserColor[] colors = { UserColor.RED, UserColor.YELLOW,
				UserColor.GREEN, UserColor.BLUE };

		// building message with text and the textfield
		Object[] message = { "Server", ipAddress, "\nLogin:", name, "\nColor:" };

		// show dialog
		UserColor ret = (UserColor) JOptionPane.showInputDialog(this, message,
				"Login", JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);

		if (ret == null)
			System.exit(0);

		socket = ClientConnection.getSocket(ipAddress.getText(), DEFAULT_PORT);

		if (socket != null) {
			// create and start communication thread
			com = new Communication(socket, this);
			com.start();

			// send login message to server with entered username and color
			com.sendMessage(new Login(name.getText(), ret));
		} else {
			// socket creation failed -> exit software
			JOptionPane.showMessageDialog(this,
					"Fehler beim Herstellen der Verbindung.");
			System.exit(0);
		}
	}

	/**
	 * sets a flag which indicates if player is admin or not
	 */
	public void setAdmin(boolean admin) {
		isAdmin = admin;
		sidebar.toogleAdmin(admin);
	}

	/**
	 * Getter for isAdmin
	 * 
	 * @return admin flag
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @return the communication
	 */
	public Communication getCommunication() {
		return com;
	}

	/**
	 * @return the sidebar
	 */
	public Sidebar getSidebar() {
		return sidebar;
	}

	/**
	 * <p>
	 * Shows player a dialog where he can choose a level
	 * 
	 * <p>
	 * Only shown if player is admin.
	 */
	public void showLevelDialog() {
		// create array with level names
		Object[] levels = new Object[model.getAvailableLevel().size()];

		for (int i = 0; i < levels.length; i++) {
			levels[i] = model.getAvailableLevel().get(i).getFilename();
		}

		// create the message
		Object message = "Wählen Sie das Level aus:";

		// show dialog
		String ret = (String) JOptionPane.showInputDialog(this, message,
				"Spieldauer", JOptionPane.QUESTION_MESSAGE, null, levels,
				levels[0]);

		// when dialog not canceled
		if (ret != null) {
			// Send message to server
			com.sendMessage(new LevelSelection(ret));

			this.setLevel(ret);
		}
	}

	/**
	 * shows player a dialog where he can choose the duration of the game only
	 * shown if player is admin
	 */
	public void showTimeDialog() {
		int ret = 0;
		// create array with play times
		Object[] time = { 5, 10, 15 };

		// create the message
		Object message = "Wählen Sie die Spieldauer in Minuten aus:";

		// show dialog
		ret = (int) JOptionPane.showInputDialog(this, message, "Spieldauer",
				JOptionPane.QUESTION_MESSAGE, null, time, time[0]);

		// Send message to server
		if (ret != 0)
			com.sendMessage(new TimeSelection(ret));
	}

	/**
	 * Returns the field
	 * 
	 * @return returns instance of the field
	 */
	public Field getField() {
		return this.field;
	}

	/**
	 * Called when {@link RoundStart} message received and the next round is
	 * about to start or the complete game starts.
	 */
	public void roundStart() {
		sidebar.startTimer();
		try {
			field.createNewField();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pack();
		repaint();
		this.requestFocus();
	}

	/**
	 * Called when {@link RoundFinished} message received and the current round
	 * ends or the complete game ends.
	 */
	public void roundFinish() {
		sidebar.stopTimer();
		model.setHaveMegaBomb(false);
		model.setHaveSuperBomb(false);
	}

	/**
	 * Called when {@link GameStart} message received and game is about to
	 * start.
	 */
	public void startGame() {
		// when game start message received also trigger round start
		roundStart();
	}

	/**
	 * called when game is finished
	 * 
	 * @param usr
	 *            array with data of each user
	 */
	public void endGame(User usr[]) {
		// when game end message received also trigger round end
		roundFinish();
		new Result(null, null, null, 0, this);
	}

	/**
	 * Set the time of the complete game.
	 * 
	 * @param time
	 *            in minutes
	 */
	public void setTime(int time) {
		sidebar.setTime(time);
	}

	/**
	 * @param userPos
	 *            the user position
	 */
	public void movePlayer(UserPosition userPos) {
		//Check if player is dead 
		if(isDead)
			return;
		//Check if player can move to wanted position
		if (model.movePlayer(userPos)) {
			// player successfully moved
			// send new position to server
			com.sendMessage(userPos);
			// replace player
			field.repositionUser(userPos.getUserID(), userPos.getPosition());
			//check if user pick up a special item
			if(model.getSpecialItem(userPos.getPosition()) != FieldType.PLAIN_FIELD){
				switch(model.getSpecialItem(userPos.getPosition())){
				case ITEM_MEGA_BOMB:
					model.setHaveMegaBomb(true);
					model.collectSpecialItem(userPos.getPosition());
					break;
				case ITEM_SUPER_BOMB:
					model.setHaveSuperBomb(true);
					model.collectSpecialItem(userPos.getPosition());
					break;
				default:
					break;
				}
						
			}
			this.repaint();
		}
	}

	/**
	 * Moves other player
	 * 
	 * @param userPos
	 *            the user position
	 */
	public void playerMoved(UserPosition userPos) {
		model.movePlayer(userPos);
		// replace player
		field.repositionUser(userPos.getUserID(), userPos.getPosition());
		//check if user pick up a special item
		if(model.getSpecialItem(userPos.getPosition()) != FieldType.PLAIN_FIELD){
			switch(model.getSpecialItem(userPos.getPosition())){
			case ITEM_MEGA_BOMB:
				model.collectSpecialItem(userPos.getPosition());
				break;
			case ITEM_SUPER_BOMB:
				model.collectSpecialItem(userPos.getPosition());
				break;
			default:
				break;
			}
					
		}
		this.repaint();
	}

	/**
	 * Updates a panel which is changed by a bomb
	 * 
	 * @param pos
	 */
	public void updatePanel(Point pos) {
		field.redrawPanel(pos);
		this.repaint();
	}

	/**
	 * Adds player in model, if he does not exist.
	 * 
	 * @param usr
	 *            User that is added
	 */
	public void addPlayer(User usr) {
		for (UserModel user : model.getUsers()) {
			if (user.getUsername().equals(usr.getUsername()))
				return;
		}

		model.addPlayer(usr);
	}

	/**
	 * Sets a new bomb in the game model and sends the placed bomb to the
	 * server.
	 * 
	 * @param bomb
	 *            Bomb that is set in the model and displayed
	 */
	public void setBomb(Bomb bomb) {
		//Check if the set bomb is not a normal bomb
		if(bomb.getBombType() != BombType.NORMAL_BOMB){
			//if it is a super or mega bomb check if the player can set one(this is possible
			//when he collected one before)
			if(bomb.getBombType() == BombType.SUPER_BOMB){
				if(!model.isHaveSuperBomb())
					return;
				model.setHaveSuperBomb(false);
			}else if(bomb.getBombType() == BombType.MEGA_BOMB){
				if(!model.isHaveMegaBomb())
					return;
				model.setHaveMegaBomb(false);
			}
		}
		this.model.setBomb(bomb);
		field.setBomb(bomb.getPosition(), bomb.getBombType());
		com.sendMessage(bomb);
	}

	/**
	 * Displays a bomb that is not set by the user
	 * 
	 * @param bomb
	 *            Bomb that is set in the model and displayed
	 */
	public void bombIsSet(Bomb bomb) {
		this.model.setBomb(bomb);
		field.setBomb(bomb.getPosition(), bomb.getBombType());
	}

	/**
	 * Returns the userID
	 * 
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Sets the user id
	 * 
	 * @param id
	 *            Player ID
	 */
	public void setUserID(int id) {
		this.userID = id;
	}

	/**
	 * Removes a dead player from the field
	 * 
	 * @param usr
	 *            Dead player
	 */
	public void playerDead(UserDead usr) {
		field.deletePlayer(usr.getUserID());
	}

	/**
	 * Removes a player which left the game due to a bad connection.
	 * 
	 * @param usr
	 *            the player to remove
	 */
	public void removePlayer(UserRemove usr) {
		model.deleteUserByID(usr.getUserID());
		field.deletePlayer(usr.getUserID());
	}
	
	/**
	 * Sets the name of the level which is chosen by the admin
	 * 
	 * @param filename
	 *            Level name
	 */
	public void setLevel(String filename) {
		model.setLevelName(filename);
		sidebar.updateLevel(filename);
	}

	/**
	 * Returns the level name
	 * 
	 * @return Level name
	 */
	public String getLevelName() {
		return model.getLevelName();
	}
	
	/**
	 * Returns id player is dead 
	 * @return true -> player is dead, false -> player is alive
	 */
	public boolean isDead() {
		return isDead;
	}
	
	/**
	 * Sets flag which shows if player is dead
	 * @param isDead true -> player is dead, false -> player is alive
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
}
