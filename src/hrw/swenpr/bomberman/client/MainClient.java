package hrw.swenpr.bomberman.client;


import hrw.swenpr.bomberman.client.listener.GameKeyListener;
import hrw.swenpr.bomberman.common.ClientConnection;
import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.LevelSelection;
import hrw.swenpr.bomberman.common.rfc.Login;
import hrw.swenpr.bomberman.common.rfc.TimeSelection;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.User.UserColor;
import hrw.swenpr.bomberman.common.rfc.UserPosition;

import java.awt.BorderLayout;
import java.awt.Point;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * <p>Main class containing {@code main()} function for starting client.</p>
 * 
 * <p>Extends {@link JFrame}.</p>
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

	private Socket socket;

	/**
	 * Starting function.
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		MainClient.getInstance();
	}
	
	
	public static MainClient getInstance() {
		if(instance == null)
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
		
		this.addKeyListener(new GameKeyListener(this));
		
		sidebar = new Sidebar(this);
		field = new Field();
		
		add(sidebar, BorderLayout.EAST);
		add(field);
		
		// show login dialog
//		this.showLogin();
		
		setVisible(true);
	}
	
	/**
	 * shows LoginWindow and sends login-request with entered data to server
	 */
	private void showLogin()
	{
		// create textfield and color array
		JTextField ipAddress = new JTextField("localhost");
		JTextField name = new JTextField(20);
		UserColor[] colors = {UserColor.RED, UserColor.YELLOW, UserColor.GREEN, UserColor.BLUE};
		
		// building message with text and the textfield
		Object[] message = {"Server", ipAddress, "\nLogin:", name, "\nColor:"};
		
		// show dialog
		UserColor ret = (UserColor) JOptionPane.showInputDialog(this, message, "Login", JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);
		
		socket = ClientConnection.getSocket(ipAddress.getText(), DEFAULT_PORT);
		
		if(socket != null) {
			// create and start communication thread
			com = new Communication(socket);
			com.start();
		
			// send login message to server with entered username and color
			com.sendMessage(new Login(name.getText(), ret));
		}
		else {
			// socket creation failed -> exit software
			JOptionPane.showMessageDialog(this, "Fehler beim Herstellen der Verbindung.");
			System.exit(0);
		}
	}
	
	/**
	 * sets a flag which indicates if player is admin or not
	 */
	public void setAdmin(boolean admin)
	{
		isAdmin = admin;
		sidebar.toogleAdmin(admin);
	}
	
	/**
	 * Getter for isAdmin
	 * @return admin flag
	 */
	public boolean isAdmin()
	{
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
	 * shows player a dialog where he can choose a level
	 * only shown if player is admin
	 */
	public void showLevelDialog()
	{
		// create array with level names
		Object[] levels = new Object[model.getAvailableLevel().size()];
		
		for(int i = 0; i < levels.length; i++)
		{
			levels[i] = model.getAvailableLevel().get(i).getFilename();
		}
		
		//create the message
		Object message = "Wählen Sie das Level aus:";
		
		// show dialog
		String ret = (String) JOptionPane.showInputDialog(this, message , "Spieldauer", JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);
		
		//Send message to server
		com.sendMessage(new LevelSelection(ret));
	}
	
	/**
	 * shows player a dialog where he can choose the duration of the game
	 * only shwon if player is admin
	 */
	public void showTimeDialog()
	{
		// create array with play times
		Object[] time = {5, 10, 15};
		
		//create the message
		Object message = "Wählen Sie die Spieldauer in Minuten aus:";
		
		// show dialog
		int ret = (int) JOptionPane.showInputDialog(this, message , "Spieldauer", JOptionPane.QUESTION_MESSAGE, null, time, time[0]);
		
		//Send message to server
		com.sendMessage(new TimeSelection(ret));
	}
	
	/**
	 * Returns the field
	 * @return returns instance of the field
	 */
	public Field getField()
	{
		return this.field;
	}
	
	/**
	 * called when a round ends
	 */
	public void roundFinish()
	{
		
	
	}
	
	/**
	 * called when a round starts
	 */
	public void roundStart()
	{
		
	}
	
	public void startGame()
	{
		
	}
	
	/**
	 * Sets the time of the hole game
	 * @param time
	 */
	public void setTime(int time)
	{
		
	}
	
	/**
	 * called when game is finished
	 * @param usr array with data of each user
	 */
	public void gameEnd(User usr[])
	{
		new Result(null, null, null, 0, this);
	}
	
	public void showUser(User usr[])
	{
		
	}
	
	/**
	 * Moves a player on the field
	 * @param usrPos
	 */
	public void movePlayer(UserPosition usrPos)
	{
		model.movePlayer(usrPos);
	}
	
	/**
	 * Adds a user to the game
	 * @param usr User that is added
	 */
	public void addPlayer(User usr)
	{
		this.model.addPlayer(usr);
	}
	
	/**
	 * Sets a new bomb in the game model
	 * @param bomb Bomb that is set in the model
	 */
	public void setBomb(Bomb bomb)
	{
		this.model.setBomb(bomb);
	}
	
	/**
	 * Returns the userID
	 * @return the userID
	 */
	public int getUserID()
	{
		return userID;
	}
	
	/**
	 * Sets the user id
	 * @param id
	 */
	public void setUserID(int id) {
		this.userID = id;
	}
	
	/**
	 * Removes a dead player
	 * @param usr Dead player
	 */
	public void playerDead(User usr)
	{
		model.removePlayer(usr);
	}

	/**
	 * is called when the game starts
	 */
	public void gameStart() 
	{
		
	}
	
	/**
	 * Adds a list of levels to the game
	 * @param level {@link ArrayList} of new levels
	 */
	public void setAvailableLevel(ArrayList<Level> level)
	{
		model.setLevels(level);
	}
}