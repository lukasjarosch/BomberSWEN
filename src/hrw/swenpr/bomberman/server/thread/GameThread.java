package hrw.swenpr.bomberman.server.thread;

import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.GameOver;
import hrw.swenpr.bomberman.common.rfc.UserPosition;
import hrw.swenpr.bomberman.server.Server;
import hrw.swenpr.bomberman.server.ServerModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameThread implements Runnable {

	/**
	 * Store the server model for easy access
	 */
	private ServerModel model;
	
	/**
	 * The {@link GameThread} constructor simply defines
	 * the local 'model' variable to ease access to the server model
	 * 
	 * @author Lukas Jarosch
	 * 
	 */
	public GameThread() {
		model = Server.getModel();
	}
	
	/**
	 * Once the thread is started the game is also started.
	 * This thread will run for the whole time until the game (not the round) ends
	 * 
	 * @author Lukas Jarosch
	 */
	@Override
	public void run() {
		
		// Game running....
		model.setGameRunning(true);
		
		// Send GameStart to clients
		
		// Run Forest...run!
		while(model.isGameRunning()) {
			
			// Wait for messages....
			
			checkRoundEnd();
		}
		
		// Send GameEnd to clients
	}
	

	
	/**
	 * Checks if there is only one player left (alive)
	 * If so the round will end and the next round will start
	 * 
	 * @author Lukas Jarosch
	 */
	private void checkRoundEnd() {
		
		// If one player is left the round is over and the player wins
		if(countAlivePlayers() == 1) {
			
			// Send RoundFinish
			
		}
	}
	
	/**
	 * Returns the amount of dead players in order to be able
	 * to check if the round is over
	 * 
	 * @return Count of dead players
	 */
	private int countAlivePlayers() {
		int alive = 0;
		ArrayList<ClientThread>clients = model.getClientThreads();
		
		while(clients.iterator().hasNext()) {
			if(clients.iterator().next().isAlive()) {
				alive++;
			}
		}
		return alive;
	}
}
