package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.thread.ClientThread;

import java.net.Socket;
import java.util.ArrayList;

public class Communication {

	/**
	 * Constructor for the {@link Communication} class
	 * 
	 * @author Lukas Jarosch
	 */
	public Communication() {
		
	}
	
	/**
	 * Send a message to a specified socket
	 * 
	 * @param socket The socket to use
	 * @param message The message to send
	 */
	public <T> void sendToClient(Socket socket, T message) {
		
	}
	
	/**
	 * Sends a message to all clients registered
	 * 
	 * @param message The message to send
	 */
	public <T> void sendToAllClients(T message){
		ArrayList<ClientThread> threads = Server.getModel().getClientThreads();
		
		while(threads.iterator().hasNext()) {
			
			// Send message to the socket: threads.iterator().next().getSocket();
		}
	}
}
