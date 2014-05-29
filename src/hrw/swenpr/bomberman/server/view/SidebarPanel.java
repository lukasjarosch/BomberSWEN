package hrw.swenpr.bomberman.server.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SidebarPanel extends JPanel {
	
	/**
	 * The label for the Score table
	 */
	private JLabel lblScore = new JLabel("Score");
	
	/**
	 * The score list
	 */
	private JList scoreList = new JList();
	
	/**
	 * {@link SidebarPanel} constructor
	 * 
	 * @author Lukas Jarosch
	 */
	public SidebarPanel() {		
		this.setLayout(new BorderLayout());
		//this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

}
