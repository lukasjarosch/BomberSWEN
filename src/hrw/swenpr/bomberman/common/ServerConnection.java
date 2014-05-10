package hrw.swenpr.bomberman.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	 * Create a socket on {@code port}.
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
	 * Waits until a new connection is established, then returns the new socket.<br>
	 * To read and write the {@link Socket} provides two functions, {@code getOutputStream()} and {@code getInputStream()},
	 * which should be casted to an {@link ObjectOutputStream} or {@link ObjectInputStream}.
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
	 * Closes the {@code ServerSocket}. Should be called when terminating the software or no new connection should be accepted.
	 */
	public void closeServer() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
