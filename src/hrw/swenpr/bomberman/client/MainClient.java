package hrw.swenpr.bomberman.client;

import javax.swing.JFrame;

public class MainClient extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new MainClient();
	}
	
	public MainClient() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bomberman");

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setLocationRelativeTo(null);
		setVisible(true);
	}

}
