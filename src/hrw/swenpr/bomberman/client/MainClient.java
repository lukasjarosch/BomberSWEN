package hrw.swenpr.bomberman.client;

import javax.swing.JFrame;

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
	public static void main(String[] args) {
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
	}

}
