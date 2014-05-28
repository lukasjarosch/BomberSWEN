package hrw.swenpr.bomberman.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.server.thread.ClientThread;

public class ServerModel extends BombermanBaseModel {

	/**
	 * How many players are ready to start the game?
	 * If this matches the total player count the game will start
	 */
	private int readyCount = 0;
	
	/**
	 * If the server is running all the threads are also
	 * allowed to run. Setting this variable to false after
	 * the server is started will stop every thread
	 */
	private boolean serverRunning = false;
	
	/**
	 * Whether the game is running or not
	 */
	private boolean gameRunning = false;
	
	/**
	 * The timer which will schedule the end of the game
	 * based on the decision of the game admin
	 */
	private Timer gameTimer;
	
	/**
	 * Store all client threads
	 */
	private ArrayList<ClientThread> clientThreads = null;
	
	/**
	 * The {@link ServerModel} constructor
	 * 
	 * @param The players participating in the game
	 * @param The level file to load
	 */
	public ServerModel(ArrayList<User> users, File level) {
		super(users, level);
	}
	
	/**
	 * We do not allow direct modification of the readyCount.
	 * A player can only click on 'Ready' but not 'unclick' it.
	 * 
	 * This will simply increase the current readyCount
	 * 
	 * It will not perform any validation!
	 * 
	 * @author Lukas Jarosch
	 */
	public void increaseReadyCount() {
		readyCount++;
	}
	
	/**
	 * Returns the current readyCount
	 * 
	 * @return The amount of players ready to play
	 * 
	 * @author Lukas Jarosch
	 */
	public int getReadyCount() {
		return readyCount;
	}
	
	/**
	 * Get server state
	 * 
	 * @return The server state
	 */
	public boolean isServerRunning() {
		return serverRunning;
	}

	/**
	 * Set the server running state
	 * 
	 * @param running
	 */
	public void setServerRunning(boolean running) {
		this.serverRunning = running;
	}

	/**
	 * Return the {@link Timer}
	 *  
	 * @return The game timer
	 */
	public Timer getGameTimer() {
		return gameTimer;
	}

	/**
	 * Set the {@link Timer} of the game
	 * 
	 * @param gameTimer
	 */
	public void setGameTimer(Timer gameTimer) {
		this.gameTimer = gameTimer;
	}

	/**
	 * Return the game status
	 * 
	 * @return Game status
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}

	/**
	 * Set the game running status
	 * 
	 * @param gameRunning
	 */
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	/**
	 * Returns all clientthreads
	 * 
	 * @return {@link ClientThread}
	 */
	public ArrayList<ClientThread> getClientThreads() {
		return clientThreads;
	}

	/**
	 * Fetches the {@link ClientThread} for a specific user
	 * 
	 * @param userId The user id
	 * @return The {@link ClientThread}
	 * 
	 * @author Lukas Jarosch
	 */
	public ClientThread getClientThread(int userId) {
		return this.clientThreads.get(userId);
	}
	
	/**
	 * Add a new client thread to the array list
	 * The index is specified by the User ID
	 * 
	 * @param userId User ID of the player
	 * @param thread The {@link ClientThread} instance
	 */
	public void setClientThread(int userId, ClientThread thread) {
		this.clientThreads.add(userId, thread);
	}
}
