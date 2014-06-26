package hrw.swenpr.bomberman.server.thread;

import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.GameStart;
import hrw.swenpr.bomberman.common.rfc.Header;
import hrw.swenpr.bomberman.common.rfc.Level;
import hrw.swenpr.bomberman.common.rfc.LevelAvailable;
import hrw.swenpr.bomberman.common.rfc.LevelFile;
import hrw.swenpr.bomberman.common.rfc.LevelSelection;
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
	 */
	@Override
	public void run() {		
		MainWindow.log(new LogMessage(LEVEL.INFORMATION, "ClientThread for User " + Server.getModel().getUserById(userId).getUsername()));

		// Admin receives a list of all levels
		if (isGameAdmin()) {
			
			// Get all available levels
			ArrayList<Level> levels = Server.getModel().getAvailableLevels();
			
			// Send to game admin
			Server.getCommunication().sendToClient(outputStream, new LevelAvailable(levels));
		}
		
		// send all currently logged-in users to new client
		ArrayList<UserModel> users = Server.getModel().getUsers();
		for (UserModel user : users) {
			Server.getCommunication().sendToClient(outputStream, new User(user.getUserID(), user.getUsername(), user.getScore(), user.getColor()));
		}
		

		// Enter working loop
		while (!Thread.interrupted()) {

			Object msg = null;

			try {
				// read object
				msg = inputStream.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// when error occurred because client logged off
				if(removed)
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "User " + userId + " logged off and closed connection"));
				else
					MainWindow.log(new LogMessage(LEVEL.ERROR, "Unable to read from InputStream."));
				
				e.printStackTrace();
				
				// exit thread
				return;
			}
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Received message of type: " + Header.getMessageType(msg)));
			
			
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
					handleUserReady();
					break;
					
				case BOMB:
					// forward all bomb messages to all clients
					Server.getCommunication().sentToAllOtherClients(msg, this);
					break;
					
				case USER_REMOVE:
					// if user was set ready -> decrease ready count
					if(Server.getModel().getUserById(userId).isReady())
						Server.getModel().decrementReadyCount();
					
					Server.getModel().removeUser(((UserRemove)msg).getUserID());
					
					// forward message to all clients
					Server.getCommunication().sentToAllOtherClients(msg, this);
					removed = true;
					break;
	
				default:
					break;
			}
		}		
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
			
			// Store level information
			Server.getModel().setLevelFilename(selection.getFilename());
			
			// send selection to all clients
			Server.getCommunication().sendToAllClients(selection);			
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
	 * Handles a {@link UserReady} message from the player
	 * 
	 * @author Lukas Jarosch
	 */
	public void handleUserReady() {
		
		ServerModel model = Server.getModel();
		
		model.incrementReadyCount();
		model.getUserById(userId).setReady(true);
		
		// If all players are ready (and at least 2 players are logged in) => start game by sending the level file
		if(model.getReadyCount() == model.getUsers().size() && model.getUsers().size() > 1) {

			// Send level file
			File file = new File(ServerModel.LEVEL_DIR + File.separator + model.getLevelFilename());
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Sending level: " + model.getLevelFilename()));
			Server.getCommunication().sendToAllClients(new LevelFile(file));
			
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
	public void setAlive(boolean alive) {
		this.alive = alive;
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
}