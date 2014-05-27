package hrw.swenpr.bomberman.server.listener;

import hrw.swenpr.bomberman.server.LogMessage;
import hrw.swenpr.bomberman.server.ServerMain;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	/**
	 * The {@link ActionListener} for the Start/Stop button
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		ServerMain.getMainWindow().log(new LogMessage(LEVEL.INFORMATION, "Button clicked"));
	}

}
