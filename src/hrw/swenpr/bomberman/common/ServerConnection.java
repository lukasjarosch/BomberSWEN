package hrw.swenpr.bomberman.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Provides an {@link ServerSocket} for accepting new connections on a specific port.
 * 
 * @author Marco Egger
 */
public class ServerConnection {
	private ServerSocket server = null;
	
	
	/**
	 * Create a socket for a port.
	 * 
	 * @param port to listen on
	 */
	public ServerConnection(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// when creation failed print error and exit program
			System.out.println("Unable to listen on port " + port);
			System.exit(0);
		}
	}
	
	
	/**
	 * Waits until a new connection is established, then returns the new socket.
	 * 
	 * @return {@link Socket} the socket
	 */
	public Socket listenSocket() {
		// reset socket
		Socket socket = null;
		
		// server.accept() blocks until a connection is established  
		try {
			socket = server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return socket;
	}
	
	
	/**
	 * To close the {@code ServerSocket}. Should be called when terminating the software or no new connection should be accepted.
	 */
	public void closeServer() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
