package hrw.swenpr.bomberman.server.thread;

import hrw.swenpr.bomberman.server.ServerMain;
import hrw.swenpr.bomberman.server.ServerModel;

import java.util.Timer;
import java.util.TimerTask;

public class GameThread implements Runnable {

	private ServerModel model;
	
	/**
	 * The {@link GameThread} constructor simply defines
	 * the local 'model' variable to ease access to the server model
	 * 
	 * @author Lukas Jarosch
	 * 
	 */
	public GameThread() {
		model = ServerMain.getModel();
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
	}

	/**
	 * Sets up a {@link Timer} which will end the game
	 * after the selected time.
	 * 
	 * The timer is stored in the {@link ServerModel}
	 * 
	 * @author Lukas Jarosch
	 */
	private void scheduleGameEnd() {
		model.setGameTimer(new Timer());
		model.getGameTimer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// End game
			}
		}, 1/* INSERT TIME */ );
	}
}
