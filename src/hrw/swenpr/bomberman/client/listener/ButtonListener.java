package hrw.swenpr.bomberman.client.listener;

import hrw.swenpr.bomberman.client.MainClient;
import hrw.swenpr.bomberman.client.Sidebar;
import hrw.swenpr.bomberman.common.rfc.UserReady;
import hrw.swenpr.bomberman.common.rfc.UserRemove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ButtonListener implements ActionListener {

	private MainClient client;
	
	public ButtonListener(MainClient client) {
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// choose level
		if(event.getActionCommand() == Sidebar.CHOOSE_LEVEL) {
			client.showLevelDialog();
		}
		
		// choose time
		if(event.getActionCommand() == Sidebar.CHOOSE_TIME) {
			client.showTimeDialog();
		}
		
		// ready
		if(event.getActionCommand() == Sidebar.READY) {
			UserReady ready = new UserReady(client.getUserID());
			client.getCommunication().sendMessage(ready);
		}
		
		// logout
		if(event.getActionCommand() == Sidebar.LOGOUT) {
			int selection = JOptionPane.showConfirmDialog(client, "Spiel wirklich verlassen?", "Achtung", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			// when yes pressed exit software
			if(selection == JOptionPane.YES_OPTION) {
				// send leave message to server
				client.getCommunication().sendMessage(new UserRemove(client.getUserID()));
				System.exit(0);
			}
		}
	}

}
