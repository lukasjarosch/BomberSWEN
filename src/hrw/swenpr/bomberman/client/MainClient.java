package hrw.swenpr.bomberman.client;

import javax.swing.JDialog;
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
		setVisible(true);
		
		this.showLogin();
	}
	
	private void showLogin()
	{
		//Dialog erzeugen und anzeigen					
		JTextField name = new JTextField(20);
		String[] colors = {"Red", "Yellow", "Green", "Blue"};
		Object[] message = {"Login:", name, "\nColor:"};
		String ret = (String)JOptionPane.showInputDialog(this, message, "Login", JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);
		
	}

}
