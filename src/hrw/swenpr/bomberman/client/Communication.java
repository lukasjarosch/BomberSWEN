package hrw.swenpr.bomberman.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import hrw.swenpr.bomberman.common.ClientConnection;
import hrw.swenpr.bomberman.common.rfc.*;


/**
 * <p> Class contains methods for communication 
 * 
 * @author Daniel Hofer
 *
 */
public class Communication 
{
	//Socket for communication with Server
	private Socket socket;
	
	public Communication()
	{
		try 
		{
			this.socket = ClientConnection.getSocket(InetAddress.getLocalHost().getHostName(), 6969);
		} catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a message to the server
	 * @param message Message that is send 
	 */
	public <T> void sendMessage(T message)
	{
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Could not send the packet");
		}
	}
	
}
