package hrw.swenpr.bomberman.server.thread;

import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage.ErrorType;
import hrw.swenpr.bomberman.common.rfc.GameOver;
import hrw.swenpr.bomberman.common.rfc.GameStart;
import hrw.swenpr.bomberman.common.rfc.Header;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.LevelAvailable;
import hrw.swenpr.bomberman.common.rfc.LevelFile;
import hrw.swenpr.bomberman.common.rfc.LevelSelection;
import hrw.swenpr.bomberman.common.rfc.RoundFinished;
import hrw.swenpr.bomberman.common.rfc.RoundStart;
import hrw.swenpr.bomberman.common.rfc.TimeSelection;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserReady;
import hrw.swenpr.bomberman.common.rfc.UserRemove;
import hrw.swenpr.bomberman.server.LogMessage;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.Server;
import hrw.swenpr.bomberman.server.ServerModel;
import hrw.swenpr.bomberman.server.view.MainWindow;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This thread is started for each client and
 * handles the communication with it
 * 
 * @author Lukas Jarosch
 * @author Marco Egger
 *
 */
public class ClientThread extends Thread {
	
	/**
	 * If the player is the game admin
	 */
	private boolean gameAdmin = false;
	
	/**
	 * Once the player dies...he's dead
	 */
	private boolean alive = true;
	
	/**
	 * The client socket
	 */
	private Socket socket = null;
	
	/**
	 * The output stream
	 */
	private ObjectOutputStream outputStream = null;
	
	/**
	 * The input stream
	 */
	private ObjectInputStream inputStream = null;
	
	/**
	 * The user id
	 */
	private int userId;

	/**
	 * Holds if the client has sent a {@link UserRemove} message
	 */
	private boolean removed = false;
	
	
	/**
	 * Creates the socket for communicating with the client
	 * 
	 * @author Lukas Jarosch
	 * @author Marco Egger
	 */
	public ClientThread(ObjectOutputStream out, ObjectInputStream in) {
		this.outputStream = out;
		this.inputStream = in;
	}
	
	/**
	 * The main method of the thread which is running in an endless
	 * loop until the game ends
	 * 
	 * @author Lukas Jarosch
	 * @author Marco Egger
	 */
	@Override
	public void run() {		
		MainWindow.log(new LogMessage(LEVEL.INFORMATION, "ClientThread for User " + Server.getModel().getUserById(userId).getUsername()));

		// Admin receives a list of all levels
		if (isGameAdmin()) {
			sendLevelList();			
		}
		
		// Send all currently logged-in users to new client
		sendUserList();
		

		// Enter working loop
		while (!Thread.interrupted()) {

			// Read object
			Object msg = readObject();
			
			// Exit thread if nothing was read
			if(msg.equals(null))
				return;			
			
			// Handle messages from client
			switch (Header.getMessageType(msg)) {
			
				case LEVEL_SELECTION:
					handleLevelSelection((LevelSelection) msg);
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Admin selected level: " + ((LevelSelection) msg).getFilename()));
					break;
					
				case TIME_SELECTION:
					handleTimeSelection((TimeSelection) msg);
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Admin selected time: " + ((TimeSelection) msg).getTime() + " minutes"));
					break;
									
				case USER_READY:
					// Increment user_ready count and start game if necessary
					handleUserReady();
					break;
					
				case BOMB:
					// Forward all bomb messages to all clients
					Server.getCommunication().sentToAllOtherClients(msg, this);
					break;
					
				case USER_REMOVE:
					// log message before deleting user else it will be null
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Player " + getLogUser() + " left."));
					handleUserRemove((UserRemove)msg);
					break;

				case USER_POSITION:
					Server.getCommunication().sentToAllOtherClients(msg, this);
					break;		
					
				case USER_DEAD:
					handleUserDead();
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Player " + getLogUser() + "is dead."));
					break;					
				
				default:
					break;
			}
		}		
	}
	
	/**
	 * Sends a list of all currently logged in users to the
	 * client.
	 * 
	 * @author Lukas Jarosch
	 */
	private void sendUserList() {
		ArrayList<UserModel> users = Server.getModel().getUsers();
		for (UserModel user : users) {
			Server.getCommunication().sendToClient(outputStream, new User(user.getUserID(), user.getUsername(), user.getScore(), user.getColor()));
		}
	}
	
	/**
	 * Sends a list of all levels to a client.
	 * Only the game admin should receive this, so make
	 * sure to check for that
	 * 
	 * @author Lukas Jarosch
	 */
	private void sendLevelList() {
		// Get all available levels
		ArrayList<Level> levels = Server.getModel().getAvailableLevels();

		// Send to game admin
		Server.getCommunication().sendToClient(outputStream, new LevelAvailable(levels));
	}
	
	/**
	 * Reads an object from the InputStream
	 * 
	 * @return The object which was read or null. If null the thread should be terminated
	 * 
	 * @author Lukas Jarosch
	 */
	private Object readObject() {
		Object msg;
		
		try {
			// read object
			msg = inputStream.readObject();
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Received message of type: " + Header.getMessageType(msg)));
		} catch (ClassNotFoundException | IOException e) {
			// when error occurred because client logged off
			if(removed)
				MainWindow.log(new LogMessage(LEVEL.INFORMATION, "User " + userId + " logged off and closed connection"));
			else
				MainWindow.log(new LogMessage(LEVEL.ERROR, "Unable to read from InputStream."));
			
			e.printStackTrace();
			
			// exit thread
			return null;
		}
		return msg;
	}

	/**
	 * Handles a {@link LevelSelection} from the game admin(!)
	 * Other players cannot select the level
	 * 
	 * @param selection the level selection
	 * 
	 * @author Lukas Jarosch
	 * @author Marco Egger
	 */
	public void handleLevelSelection(LevelSelection selection) {
		if(isGameAdmin()) {
			
			// when no round played -> waiting for UserReady messages from all clients
			if(Server.getModel().getRoundCount() == 0) {
				// Store level information
				Server.getModel().setLevelFilename(selection.getFilename());
				
				// send selection to all clients
				Server.getCommunication().sendToAllClients(selection);
			}
			else {
				// at least one round played -> LevelSelection triggers RoundStart
				
				// set level in model
				Server.getModel().setLevelFilename(selection.getFilename());
				// send selection to all clients
				Server.getCommunication().sendToAllClients(selection);
				
				// send level file
				sendLevelFile(selection.getFilename());
				
				// start round
				Server.getModel().roundStart();
				Server.getCommunication().sendToAllClients(new RoundStart());
			}
		}
	}
	
	/**
	 * Handles a dying user. If one or less players are left
	 * the game will end
	 * 
	 * @author Marco Egger
	 */
	public void handleUserDead() {
		ServerModel model = Server.getModel();
		setAlive(false);
		
		int numDead = 0;
		for (ClientThread client : model.getClientThreads()) {
			if(client.clientIsAlive()) 
				numDead++;
		}
		
		// Game/round ends when one or less players are alive
		if(model.getClientCount() - 1 <= numDead) {
			// check the number of rounds played
			if(model.getRoundCount() >= ServerModel.DEFAULT_ROUND_AMOUNT) {
				Server.getCommunication().sendToAllClients(new GameOver());
			}
			else {
				// if game not finished completely -> increase round count and send RoundFinished message to all clients
				model.roundFinished();
				Server.getCommunication().sendToAllClients(new RoundFinished());
			}
		}
	}
	
	/**
	 * Removes a user from the model and sends messages to other clients with important
	 * information such as the game admin left or there are not enough players during game phase.
	 * 
	 * @param msg The {@link UserRemove}
	 * 
	 * @author Marco Egger
	 */
	public void handleUserRemove(UserRemove msg) {
		ServerModel model = Server.getModel();
		
		// if user was set ready -> decrease ready count
		if(model.getUserById(userId).isReady())
			model.decrementReadyCount();
		
		model.removeUser(msg.getUserID());
		model.removeClientThread(this);
		
		// forward message to all clients
		Server.getCommunication().sentToAllOtherClients(msg, this);
		removed = true;
		
		// if game is running
		if(model.isGameRunning()) {
			// when only one player logged in
			if(model.getUsers().size() <= 1) {
				Server.getCommunication().sendToAllClients(new ErrorMessage(ErrorType.ERROR, "Zu wenig Spieler vorhanden!"));
			}
		}
		
		// if game admin left -> end game
		if(isGameAdmin()) {
			Server.getCommunication().sendToAllClients(new ErrorMessage(ErrorType.ERROR, "Spielleiter hat das Spiel verlassen."));
		}
	}
	
	/**
	 * Handles a {@link TimeSelection} from the game admin(!)
	 * Other players cannot select the time
	 * 
	 * @param selection the time selection
	 * 
	 * @author Lukas Jarosch
	 * @author Marco Egger
	 */
	public void handleTimeSelection(TimeSelection selection) {
		if(isGameAdmin()) {
			// store time
			Server.getModel().setGameTime(selection.getTime());
			
			// send selection to all clients
			Server.getCommunication().sendToAllClients(selection);
		}
	}
	
	/**
	 * Handles a {@link UserReady} message from the player.
	 * 
	 * @author Lukas Jarosch
	 * @author Marco Egger
	 */
	public void handleUserReady() {
		ServerModel model = Server.getModel();
		
		// check if admin
		if(isGameAdmin()) {
			// check if he selected a level and a time
			if(!model.isReadyToStart()) {
				// send error message to notify about missing selection
				Server.getCommunication().sendToClient(outputStream, new ErrorMessage(ErrorType.WARNING, "Du musst ein Level und die Spielzeit auswählen!"));
				return;
			}
		}
		
		// only increase ready count if user has not been set to ready already
		if(!model.getUserById(userId).isReady()) {
			model.incrementReadyCount();
			model.getUserById(userId).setReady(true);
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Player " + getLogUser() +  " ready."));
		}
		
		// If all players are ready (and at least 2 players are logged in) => start game by sending the level file
		if(model.getReadyCount() == model.getUsers().size() && model.getUsers().size() > 1) {

			// send level file
			sendLevelFile(model.getLevelFilename());
			
			// Start game
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Game started."));
			Server.getCommunication().sendToAllClients(new GameStart());
		}
	}

	/**
	 * Is the player still alive?
	 * 
	 * @return boolean
	 */
	public boolean isPlayerAlive() {
		return alive;
	}

	/**
	 * Set the player to dead or alive (Schrödinger....)
	 * 
	 * @param alive
	 */
	private void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * Returns whether 
	 * @return
	 */
	public boolean clientIsAlive() {
		return alive;
	}

	/**
	 * Get the client socket 
	 * 
	 * @return Socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Set the client socket  and the output stream
	 * @param socket Socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean isGameAdmin() {
		return gameAdmin;
	}

	public void setGameAdmin(boolean gameAdmin) {
		this.gameAdmin = gameAdmin;
	}

	public int getUserId() {
		return userId;
	}

	public void setId(int id) {
		this.userId = id;
	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	/**
	 * Sends a {@link File} to all clients. Internal use to send the level file to all clients.
	 * 
	 * @param filename the filename of the level to send
	 * 
	 * @author Marco Egger
	 */
	private void sendLevelFile(String filename) {
		// create reference to the file
		File file = new File(ServerModel.LEVEL_DIR + File.separator + filename);
		// print log
		MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Sending level: " + filename));
		// send file over sockets
		Server.getCommunication().sendToAllClients(new LevelFile(file));
	}
	
	/**
	 * Returns a {@link String} in the following format:
	 * 'Playername' (userID)
	 * 
	 * @return a String for the {@link MainWindow} log.
	 * 
	 * @author Marco Egger
	 */
	private String getLogUser() {
		return "'" + Server.getModel().getUserById(userId).getUsername() + "' (" + userId + ")";
	}
}