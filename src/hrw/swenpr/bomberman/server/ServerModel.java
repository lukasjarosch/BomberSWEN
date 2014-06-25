package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.server.thread.ClientThread;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

public class ServerModel extends BombermanBaseModel {
	
	public static final String LEVEL_DIR = System.getProperty("user.dir") + File.separator +  "level";

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
	 * Holds the number of clients logged in
	 */
	private int clientCount = 0;
	
	/**
	 * Store all client threads
	 */
	private ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
	
	/**
	 * The selected level filename
	 */
	private String levelFilename = null;
	
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
	 * Empty default constructor
	 */
	public ServerModel() {}
	
	/**
	 * Set the filename of the selected level.
	 * 
	 * @param filename the filename
	 */
	public void setLevelFilename(String filename) {
		levelFilename = filename;
	}
	
	public String getLevelFilename() {
		return levelFilename;
	}
	
	/**
	 * Fetch a {@link UserModel} by ID
	 * 
	 * @param id
	 * @return The {@link UserModel} or null
	 * 
	 * @author Lukas Jarosch
	 * @author Marco Egger
	 */
	public UserModel getUserById(int id) {
		for(UserModel user : getUsers()) {
			if(user.getUserID() == id)
				return user;
		}
		return null;
	}
	
	public ArrayList<Level> getAvailableLevels() {
		ArrayList<Level> levels = new ArrayList<Level>();
		
		File levelDir = new File(LEVEL_DIR);
		File[] levelFiles = levelDir.listFiles();
		
		for (int i = 0; i < levelFiles.length; i++) {
			File file = levelFiles[i];
			
			levels.add(new Level(file.getName(), null));
		}
		
		return levels;
	}
	
	/**
	 * We do not allow direct modification of the readyCount.
	 * A player can only click on 'Ready' but not 'unclick' it.
	 * 
	 * This will simply increments the current readyCount
	 * 
	 * It will not perform any validation!
	 * 
	 * @author Lukas Jarosch
	 */
	public void incrementReadyCount() {
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
	public void addClientThread(int userId, ClientThread thread) {
		this.clientThreads.add(userId, thread);
	}

	/**
	 * Returns the amount of players currently logged in 
	 * 
	 * @return Number of players logged in
	 * 
	 * @author Lukas Jarosch
	 */
	public int getClientCount() {
		return clientCount;
	}

	/**
	 * Increments the client count
	 * 
	 * @param clientCount
	 * 
	 * @author Lukas Jarosch
	 */
	public void incrementClientCount() {
		clientCount = getClientCount() + 1;
	}
	
	/**
	 * Returns all users logged in
	 * 
	 * @return All {@link UserModel}s
	 * 
	 * @author Lukas Jarosch
	 */
	public ArrayList<UserModel> getUsers() {
		return this.users;
	}
}
