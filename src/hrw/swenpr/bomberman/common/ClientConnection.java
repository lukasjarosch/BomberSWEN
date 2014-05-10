package hrw.swenpr.bomberman.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Class provides functions for establishing a connection on client side.
 * 
 * @author Marco Egger
 */
public class ClientConnection {

	/**
	 * {@code Static} function to create a socket to communicate with the {@code host} on specific {@code port}.<br>
	 * To read and write the socket provides two functions, {@code getOutputStream()} and {@code getInputStream()},
	 * which should be casted to an {@link ObjectOutputStream} or {@link ObjectInputStream}.
	 * 
	 * @param host IP address or machine name
	 * @param port the server has to listen on this port
	 * @return {@link Socket} the socket, can be {@code null} when an error occurred
	 */
	public static Socket getSocket(String host, int port) {
		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByName(host), port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to create socket to " + host + " on port " + port);
		}
		
		return socket;
	}
}
