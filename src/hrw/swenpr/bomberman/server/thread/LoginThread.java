package hrw.swenpr.bomberman.server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;

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

public class LoginThread implements Runnable {

	/**
	 * The login loop which is constantly waiting for a login request
	 * by a client.
	 * It is then processed and a response is sent
	 * 
	 * @author Lukas Jarosch
	 */
	@Override
	public void run() {
		
		// Loop until the server is about to shut down
		while(Server.getModel().isServerRunning()) {
			
			// Wait for client connection
			Socket clientSocket = Server.getConnection().listenSocket();
			
			// A new client has connected
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Client {NAME} is requesting a login"));
			
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
				
				if(requestLogin()) {
					
					MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Player " + login.getUsername() + " logged in"));
					
					// TODO: This can not be random!!!!
					Server.getCommunication().sendToClient(clientSocket, 
							new LoginOk(new Random().nextInt(4)));			
					
					// Add player to model
					addPlayer(login);
					
					// Inform all clients
					sendUserData(login);
				} 
				
			// No LOGIN object received => ErrorMessage
			} else {
				MainWindow.log(new LogMessage(LEVEL.WARNING, "Client did not send a LOGIN request"));
				
				Server.getCommunication().sendToClient(clientSocket, 
						new ErrorMessage(ErrorType.ERROR, "First packet has to be a Login object"));			
				}
			
		}
		
		// Kill tha bitch
	}
	
	/**
	 * Waits for a login message
	 * The message is then processed and a 
	 * {@link ClientThread} is created
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @return Whether to accept further logins or not
	 */
	private boolean requestLogin() {
		
		// Test if the game is already running => FALSE
		// If game is running: Log: "Game already running. Login rejected"
		// TODO: Send ErrorMessage
		
		// Test how many players are logged in => FALSE
		// If 4 players are already logged in: Log: "Too many players. Login rejected"
		// TODO: Send ErrorMessage
		
		// Check if name is already registered
		// TODO: Send ErrorMessage
		
		// Player is ok to join 
		return true;
	}
	
	/**
	 * Handles the creation of a new player
	 * 
	 * @param Login message
	 * 
	 * @author Lukas Jarosch
	 */
	private void addPlayer(Login login) {
		
		// Log: "PLAYERNAME has requested a login. Processing..."
		
		// Add player model to the server model container
		
		// Create ClientThread
	}
	
	/**
	 * Sends a {@link User} object to all clients
	 * 
	 * @param Login message
	 * 
	 * @author Lukas Jarosch
	 */
	private void sendUserData(Login login) {
		
		// Create User object
		
		// Send message to all connected clients
		
	}
}
