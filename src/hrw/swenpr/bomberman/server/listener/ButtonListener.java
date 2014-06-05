package hrw.swenpr.bomberman.server.listener;

import hrw.swenpr.bomberman.server.LogMessage;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.Server;
import hrw.swenpr.bomberman.server.view.MainWindow;

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
		
		if(Server.getModel().isServerRunning()) {
			MainWindow.log(new LogMessage(LEVEL.NONE, "## Server terminated"));
			MainWindow.getMainPanel().getBtnStartStop().setText("Start server");
			Server.getModel().setServerRunning(false);
			
			Server.shutdown();
		} else {
			MainWindow.log(new LogMessage(LEVEL.NONE, "## Server running"));
			MainWindow.getMainPanel().getBtnStartStop().setText("Stop server");
			Server.getModel().setServerRunning(true);
			
			Server.start();
		}		
	}

}
