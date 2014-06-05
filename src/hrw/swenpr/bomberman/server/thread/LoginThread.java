package hrw.swenpr.bomberman.server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage;
import hrw.swenpr.bomberman.common.rfc.Header;
import hrw.swenpr.bomberman.common.rfc.Login;
import hrw.swenpr.bomberman.common.rfc.LoginOk;
import hrw.swenpr.bomberman.common.rfc.MessageType;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage.ErrorType;
import hrw.swenpr.bomberman.server.LogMessage;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.Server;
import hrw.swenpr.bomberman.server.view.MainWindow;

public class LoginThread extends Thread {

	/**
	 * The login loop which is constantly waiting for a login request
	 * by a client.
	 * It is then processed and a response is sent
	 * 
	 * @author Lukas Jarosch
	 */
	public synchronized void run() {
		
		// Loop until the server is about to shut down
		while(Server.getModel().isServerRunning()) {
			
			// Wait for client connection
			Socket clientSocket = Server.getConnection().listenSocket();
			
			// The uid for the next player to login
			int uid = Server.getModel().getClientCount();
			
			// A new client has connected
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "A client (IP: " + clientSocket.getInetAddress().toString() + ") is requesting login"));
			
			// Create dummy object for message
			Object message = null;
			
			// Fetch ObjectInputStream and read Object
			try {
				ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
				message = input.readObject();
			} catch (IOException | ClassNotFoundException e) {
				MainWindow.log(new LogMessage(LEVEL.ERROR, "Unable to read from Socket in LoginThread::run()"));
			}
			
			// We need to have a LOGIN message
			MessageType type = Header.getMessageType(message);
			if(type == MessageType.LOGIN) {
				
				// We received a Login object => cast
				Login login = (Login)message;
				
				if(requestLogin(clientSocket, login)) {
					
					// Log actions
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Player '" + login.getUsername() + "' logged in"));
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, login.getUsername() + " is color " + login.getColor().toString()));
					
					// Create UID and reply with LoginOK
					Server.getCommunication().sendToClient(clientSocket, new LoginOk(uid));			
					
					// Create user
					User user = new User(uid, login.getUsername(), 0, login.getColor());
					
					// Add player to model
					addPlayer(user, clientSocket);
					
					// Inform all clients
					sendUserData(user);
				} 
				
			// No LOGIN object received => ErrorMessage
			} else {
				MainWindow.log(new LogMessage(LEVEL.WARNING, "Client did not send a LOGIN request"));
				
				Server.getCommunication().sendToClient(clientSocket, 
						new ErrorMessage(ErrorType.ERROR, "First packet has to be a Login object"));			
				}
		}
	}
	
	/**
	 * Waits for a login message
	 * The message is then processed and a 
	 * {@link ClientThread} is created
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @param The socket which requested the login
	 * @param The user who requested the login
	 * 
	 * @return Whether to accept further logins or not
	 */
	private boolean requestLogin(Socket socket, Login login) {
		
		// Deny login if game is already running
		if(Server.getModel().isGameRunning()) {
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Game is already running!"));			
			Server.getCommunication().sendToClient(socket, new ErrorMessage(ErrorType.ERROR, "Game is already running"));
			
			return false;
		}		
		
		// Deny login if color is already taken
		Iterator<UserModel> it = Server.getModel().getUsers().iterator();
		while(it.hasNext()) {
			if(it.next().getColor() == login.getColor()) {
				MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Color is already taken!"));			
				Server.getCommunication().sendToClient(socket, new ErrorMessage(ErrorType.ERROR, "Color already taken"));
				
				return false;
			}
		}
		
		// Deny login if server is already full
		if(Server.getModel().getClientCount() == 4) {
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Server is full!"));				
			Server.getCommunication().sendToClient(socket, new ErrorMessage(ErrorType.ERROR, "Server full"));
			
			return false;
		}
		
		// Check if name is already registered
		if(!usernameAvailable(login.getUsername())) {
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Username not available!"));				
			Server.getCommunication().sendToClient(socket, new ErrorMessage(ErrorType.ERROR, "Username not available"));
			
			return false;
		}		
		
		// Player is ok to join 
		return true;
	}
	
	/**
	 * Handles the creation of a new player
	 * 
	 * @param User user
	 * @param Socket client socket
	 * 
	 * @author Lukas Jarosch
	 */
	private void addPlayer(User user, Socket socket) {
		
		// Add player model to the server model container
		Server.getModel().addPlayer(user);
		
		// Increment client count
		Server.getModel().incrementClientCount();
		
		// Create and start ClientThread
		ClientThread thread = new ClientThread();
		thread.setSocket(socket);
		thread.setId(user.getUserID());
		thread.start();
		Server.getModel().addClientThread(user.getUserID(), thread);
		
		
		// Player with UID 0 is admin
		if(user.getUserID() == 0) {
			thread.setGameAdmin(true);
		}
	}
	
	/**
	 * Checks if the given username is available 
	 * 
	 * @param The username to check
	 * 
	 * @return True if the name is available
	 * 
	 * @author Lukas Jarosch
	 */
	private boolean usernameAvailable(String username) {
		Iterator<UserModel> it = Server.getModel().getUsers().iterator();
		while(it.hasNext()) {
			if(it.next().getUsername().equals(username)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Sends a {@link User} object to all clients
	 * 
	 * @param User user
	 * 
	 * @author Lukas Jarosch
	 */
	private void sendUserData(User user) {
		
		// Send message to all connected clients
		Server.getCommunication().sendToAllClients(user);		
	}
}
