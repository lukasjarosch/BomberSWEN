package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.GameOver;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserRemove;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.thread.ClientThread;
import hrw.swenpr.bomberman.server.view.MainWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ServerModel extends BombermanBaseModel {
	
	/**
	 * Default location of the level files.
	 */
	public static final String LEVEL_DIR = System.getProperty("user.dir") + File.separator +  "level";
	
	/**
	 * The default number of rounds to be played to finish a whole game.
	 */
	public static final int DEFAULT_ROUND_AMOUNT = 3;

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
	 * The time a whole game lasts in seconds
	 */
	private long gameTime = 0;
	
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
	 * Holds the number of rounds played
	 */
	private int roundCount = 0;
	
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
	
	/**
	 * Reads all files in {@code LEVEL_DIR}.
	 * 
	 * @return {@link ArrayList} with all {@link File}s.
	 * 
	 * @author Marco Egger
	 */
	public ArrayList<Level> getAvailableLevels() {
		ArrayList<Level> levels = new ArrayList<Level>();
		
		// create necessary variables
		File levelDir = new File(LEVEL_DIR);
		File[] levelFiles = levelDir.listFiles();
		
		// go through all found Files
		for (int i = 0; i < levelFiles.length; i++) {
			File file = levelFiles[i];
			
			// add the file if it's a "real" file
			if(file.isFile())
				levels.add(new Level(file.getName()));
		}
		
		return levels;
	}
	
	/**
	 * We do not allow direct modification of the readyCount.
	 * A player can only click on 'Ready' but not 'unclick' it.
	 * 
	 * This will simply increment the current readyCount
	 * 
	 * It will not perform any validation!
	 * 
	 * @author Lukas Jarosch
	 */
	public void incrementReadyCount() {
		readyCount++;
	}
	
	/**
	 * We do not allow direct modification of the readyCount.
	 * A player can only click on 'Ready' but not 'unclick' it,
	 * but it can be decreased because of an {@link UserRemove} message.
	 * 
	 * This will simply decrement the current readyCount
	 * 
	 * It will not perform any validation!
	 * 
	 * @author Marco Egger
	 */
	public void decrementReadyCount() {
		readyCount--;
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
	 * <p>Returns the game time in minutes. If the game already started
	 * only every full 60 seconds will be counted.
	 * 
	 * <p>Example:<br>
	 * 4 minutes 36 seconds -> return value: 4<br>
	 * 5 minutes 0 seconds -> return value 5<br>
	 * 
	 * @return the game time in minutes
	 * 
	 * @author Marco Egger
	 */
	public int getGameTimeInMinutes() {
		return (int) (gameTime / 60);
	}

	/**
	 * @return the gameTime in seconds
	 */
	public long getGameTimeInSeconds() {
		return gameTime;
	}

	/**
	 * @param gameTime the gameTime to set in minutes
	 */
	public void setGameTime(int gameTime) {
		this.gameTime = gameTime * 60;
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
	 * Removes a {@link ClientThread} instance when the client send a {@link UserRemove} message.
	 * 
	 * @param thread the client thread
	 * 
	 * @author Marco Egger
	 */
	public void removeClientThread(ClientThread thread) {
		clientThreads.remove(thread);
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
	 * @author Lukas Jarosch
	 */
	public void incrementClientCount() {
		clientCount++;
	}
	
	/**
	 * Decrements the client count.
	 * 
	 * @author Marco Egger
	 */
	public void decrementClientCount() {
		clientCount--;
	}
	
	
	/**
	 * Removes the user from the model with the userID.
	 * 
	 * @param userID the userID
	 * 
	 * @author Marco Egger
	 */
	public void removeUser(int userID) {
		Iterator<UserModel> it = users.iterator();
		while(it.hasNext()) {
			UserModel user = it.next();
			
			// if users match remove the current user
			if(user.getUserID() == userID) {
				it.remove();
				decrementClientCount();
			}
		}
	}

	/**
	 * Returns the amount of rounds played.
	 * 
	 * @return the roundCount
	 * 
	 * @author Marco Egger
	 */
	public int getRoundCount() {
		return roundCount;
	}

	/**
	 * <p>Increases the counter for rounds already played.
	 * 
	 * <p>This does NOT send any message to a client.
	 * 
	 * @param roundCount the roundCount to set
	 * 
	 * @author Marco Egger
	 */
	public void roundFinished() {
		roundCount++;
		
		// stop timer and free all TimerTasks
		gameTimer.cancel();
		gameTimer.purge();
		
		// print log
		MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Round finished."));
	}
	
	/**
	 * <p>Starts a new round. Schedules a {@link TimerTask} every second to decrease game time.
	 * 
	 * <p>This does NOT send any message to a client.
	 * 
	 * @author Marco Egger
	 */
	public void roundStart() {
		// set timer to schedule every second
		gameTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// decrease time
				gameTime--;
				
				// check if game is over
				if(gameTime <= 0) {
					// set game to not running and notify the clients
					setGameRunning(false);
					Server.getCommunication().sendToAllClients(new GameOver());
				}
			}
		}, 1000, 1000);
		
		// print log
		MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Round started."));
	}
	
	/**
	 * Checks if a level and a game time are selected by the game admin.
	 * 
	 * @return {@code true} when both are selected, else {@code false}
	 * 
	 * @author Marco Egger
	 */
	public boolean isReadyToStart() {
		if(levelFilename != null && gameTime != 0)
			return true;
		else
			return false;
	}
}
