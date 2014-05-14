package hrw.swenpr.bomberman.client;


import java.awt.BorderLayout;
import java.awt.Container;

import hrw.swenpr.bomberman.common.rfc.Login;
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
		
		
		com = new Communication();
		sidebar = new Sidebar();
		
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(sidebar, BorderLayout.EAST);
		
		setVisible(true);
		//show login dialog
		this.showLogin();
	}
	
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
}
