package hrw.swenpr.bomberman.server.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MainPanel extends JPanel {
	
	/**
	 * The {@link JTextArea} which displays the server logs
	 */
	private JTextArea logArea = new JTextArea(20, 50);
	
	/**
	 * The {@link JScrollPane} which holds the {@link JTextArea}
	 */
	private JScrollPane logScrollPane = new JScrollPane(logArea);
	
	/**
	 * {@link MainPanel} constructor
	 * 
	 * @author Lukas Jarosch
	 */
	public MainPanel() {
		createLogArea();
	}
	
	/**
	 * Adds the {@link JScrollPane} and the {@link JTextArea} to the {@link MainPanel}
	 * 
	 * @author Lukas Jarosch Lukas Jarosch
	 */
	private void createLogArea() {
		logArea.setEditable(false);
		logScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(logScrollPane);
	}

	/**
	 * @return The log area object
	 */
	public JTextArea getLogArea() {
		return logArea;
	}

}
