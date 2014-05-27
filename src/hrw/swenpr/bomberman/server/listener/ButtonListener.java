package hrw.swenpr.bomberman.server.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	/**
	 * The {@link ActionListener} for the Start/Stop button
	 * 
	 * @author Lukas Jarosch
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		/*
		 * The following does not yet work because we do not have an instance of
		 * the ServerModel yet
		 * 
		if(ServerMain.getModel().isServerRunning()) {
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "----- Server terminated -----"));
			ServerMain.getModel().setServerRunning(false);
		} else {
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "----- Server running -----"));
			ServerMain.getModel().setServerRunning(true);
		}
		*/
	}

}
