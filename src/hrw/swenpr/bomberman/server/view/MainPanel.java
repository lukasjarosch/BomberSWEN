package hrw.swenpr.bomberman.server.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link JTextArea} which displays the server logs
	 */
	private JTextArea logArea = new JTextArea(25, 50);
	
	/**
	 * The {@link JScrollPane} which holds the {@link JTextArea}
	 */
	private JScrollPane logScrollPane = new JScrollPane(logArea);
	
	/**
	 * The {@link JPanel} which holds the control buttons
	 */
	private JPanel controlPanel = new JPanel();
	
	/**
	 * The {@link JButton} for starting and stopping the server
	 */
	private JButton btnStartStop = new JButton();
	
	/**
	 * {@link MainPanel} constructor
	 * 
	 * @author Lukas Jarosch
	 */
	public MainPanel() {
		this.setLayout(new BorderLayout());		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		createLogArea();
		createControlPanel();
	}
	
	/**
	 * Adds the {@link JScrollPane} and the {@link JTextArea} to the {@link MainPanel}
	 * 
	 * @author Lukas Jarosch Lukas Jarosch
	 */
	private void createLogArea() {
		logArea.setEditable(false);
		logScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(logScrollPane, BorderLayout.NORTH);
	}

	/**
	 * Adds the buttons to the buttonPanel and to the mainPanel
	 * 
	 * @author Lukas Jarosch
	 */
	private void createControlPanel() {		
		btnStartStop.setText("Start server"); // Server is stopped by default
		controlPanel.add(btnStartStop);
		
		add(controlPanel, BorderLayout.SOUTH);
	}
	/**
	 * @return The log area object
	 */
	public JTextArea getLogArea() {
		return logArea;
	}

}
