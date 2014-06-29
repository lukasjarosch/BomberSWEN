package hrw.swenpr.bomberman.server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
	 * The output stream for each client
	 */
	private ObjectOutputStream clientOut = null;
	
	/**
	 * The output stream for each client
	 */
	private ObjectInputStream clientIn = null;

	/**
	 * The login loop which is constantly waiting for a login request
	 * by a client.
	 * It is then processed and a response is sent
	 * 
	 * @author Lukas Jarosch
	 */
	public synchronized void run() {
		
		while(Server.getModel().isServerRunning()) {
			
			// Wait for client connection
			Socket clientSocket = Server.getConnection().listenSocket();
			
			// Prepare uid and an empty object
			int uid = Server.getModel().getClientCount();
            Object message = null;

            // Create output stream
            try {
				clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// A new client has connected
			MainWindow.log(new LogMessage(LEVEL.INFORMATION, "A client (IP: " + clientSocket.getInetAddress().toString().substring(1) + ") is requesting login"));
						
			// Try to read from the ObjectInputStream
			try {
				clientIn = new ObjectInputStream(clientSocket.getInputStream());
				message = clientIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				MainWindow.log(new LogMessage(LEVEL.ERROR, "Unable to read from Socket in LoginThread::run()"));
			}
			
			// We need to have a LOGIN message
			MessageType type = Header.getMessageType(message);
			if(type == MessageType.LOGIN) {
				
				// We received a Login object => handle login
				Login login = (Login)message;
				
				if(requestLogin(clientOut, login)) {
					handleLogin(login, clientSocket, uid);
				} 
				
			// No LOGIN object received => ErrorMessage
			} else {
				MainWindow.log(new LogMessage(LEVEL.WARNING, "Client did not send a LOGIN request"));
				
				Server.getCommunication().sendToClient(clientOut, 
						new ErrorMessage(ErrorType.ERROR, "First packet has to be a Login object"));			
			}
		}
	}
        
        /**
         * Handles a login request by a client
         * 
         * @author Lukas Jarosch
         * 
         * @param The Login packet
         * @param The socket from which the login was requested
         * @param The uid of the new user
         */
        private void handleLogin(Login login, Socket clientSocket, int uid) {
            // Log actions
            MainWindow.log(new LogMessage(LEVEL.INFORMATION, "Player '" + login.getUsername() + "' logged in"));
            MainWindow.log(new LogMessage(LEVEL.INFORMATION, login.getUsername() + " is color " + login.getColor().toString()));
            
            if(uid == 0) 
            	System.out.println("Player " + login.getUsername() + " is admin");

            // Create UID and reply with LoginOK
            Server.getCommunication().sendToClient(clientOut, new LoginOk(uid));

            // Create user
            User user = new User(uid, login.getUsername(), 0, login.getColor());

            // Add player to model
            addPlayer(user, clientSocket);

            // Inform all clients
            sendUserData(user);
        }
	
	/**
	 * Waits for a login message
	 * The message is then processed and a 
	 * {@link ClientThread} is created
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @param The output stream which requested the login
	 * @param The user who requested the login
	 * 
	 * @return Whether to accept further logins or not
	 */
	private boolean requestLogin(ObjectOutputStream out, Login login) {
		
		// Deny login if game is already running
		if(Server.getModel().isGameRunning()) {
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Game is already running!"));			
			Server.getCommunication().sendToClient(out, new ErrorMessage(ErrorType.ERROR, "Game is already running"));
			
			return false;
		}		
		
		// Deny login if color is already taken
		Iterator<UserModel> it = Server.getModel().getUsers().iterator();
		while(it.hasNext()) {
			if(it.next().getColor() == login.getColor()) {
				MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Color is already taken!"));			
				Server.getCommunication().sendToClient(out, new ErrorMessage(ErrorType.ERROR, "Color already taken"));
				
				return false;
			}
		}
		
		// Deny login if server is already full
		if(Server.getModel().getClientCount() == 4) {
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Server is full!"));				
			Server.getCommunication().sendToClient(out, new ErrorMessage(ErrorType.ERROR, "Server full"));
			
			return false;
		}
		
		// Check if name is already registered
		if(!usernameAvailable(login.getUsername())) {
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Login rejected. Username not available!"));				
			Server.getCommunication().sendToClient(out, new ErrorMessage(ErrorType.ERROR, "Username not available"));
			
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
		ClientThread thread = new ClientThread(clientOut, clientIn);
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
