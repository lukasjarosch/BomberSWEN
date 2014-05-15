package hrw.swenpr.bomberman.client;


import java.awt.BorderLayout;
import java.awt.Container;

import hrw.swenpr.bomberman.client.listener.GameKeyListener;
import hrw.swenpr.bomberman.common.rfc.Login;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.User.UserColor;

import javax.swing.JButton;
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
	
	private Communication com;
	private Sidebar sidebar;
	private Field field;
	
	private boolean isAdmin;

	/**
	 * Starting function.
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new MainClient();
	}
	
	
	/**
	 * Default constructor for main window.
	 */
	public MainClient() {
		// setting up JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bomberman");

		// make window full-screen
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// position and show window
		setLocationRelativeTo(null);
		
		this.addKeyListener(new GameKeyListener(this));
		
		com = new Communication();
		sidebar = new Sidebar(this);
		field = new Field();
		
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(sidebar, BorderLayout.EAST);
		
		setVisible(true);
		//show login dialog
		this.showLogin();
	}
	
	/**
	 * shows LoginWindow and sends login-request with entered data to server
	 */
	private void showLogin()
	{
		//create textfield and color array				
		JTextField name = new JTextField(20);
		UserColor[] colors = {UserColor.RED, UserColor.YELLOW, UserColor.GREEN, UserColor.BLUE};
		
		//building message with text and the textfield
		Object[] message = {"Login:", name, "\nColor:"};
		
		//show dialog
		UserColor ret = (UserColor)JOptionPane.showInputDialog(this, message, "Login", JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);
		
		//Send login message to server with entered username and color
		com.sendMessage(new Login(name.getText(), ret));
	}
	
	/**
	 * sets a flag which indicates if player is admin or not
	 */
	public void setAdmin()
	{
		isAdmin = true;
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
	 * resets the isAdmin Flag
	 */
	public void resetAdmin()
	{
		isAdmin = false;
	}
	
	/**
	 * logs player out
	 */
	public void logout()
	{
		
	}
	
	/**
	 * shows player a dialog where he can choose a level
	 * only shown if player is admin
	 */
	public void showLevelialog()
	{
	
	}
	
	/**
	 * shows player a dialog where he can choose the duration of the game
	 * only shwon if player is admin
	 */
	public void showTimeDialog()
	{
		
	}
	
	/**
	 * getter for fiel
	 * @return returns instance of the field
	 */
	public Field getField()
	{
		return this.field;
	}
	
	/**
	 * called when a round ends
	 * @param usr array with data of each user
	 */
	public void roundEnd(User usr[])
	{
		new Result(null, null, null, 0);
	
	}
	
	/**
	 * called when game is finished
	 * @param usr array with data of each user
	 */
	public void gameEnd(User usr[])
	{
		
	}
		
}
