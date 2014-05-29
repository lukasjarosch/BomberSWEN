package hrw.swenpr.bomberman.client.listener;

import hrw.swenpr.bomberman.client.MainClient;
import hrw.swenpr.bomberman.client.Sidebar;
import hrw.swenpr.bomberman.common.rfc.UserReady;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	private MainClient client;
	
	public ButtonListener(MainClient client) {
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// choose level
		if(event.getActionCommand() == Sidebar.CHOOSE_LEVEL) {
			// TODO show dialog to choose level
		}
		
		// choose time
		if(event.getActionCommand() == Sidebar.CHOOSE_TIME) {
			// TODO show dialog to choose time
		}
		
		// ready
		if(event.getActionCommand() == Sidebar.READY) {
			UserReady ready = new UserReady(client.getUserID());
			client.getCommunication().sendMessage(ready);
		}
		
		// logout
		if(event.getActionCommand() == Sidebar.LOGOUT) {
			// quit software
			System.exit(0);
		}
	}

}
