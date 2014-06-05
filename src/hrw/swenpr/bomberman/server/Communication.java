package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.thread.ClientThread;
import hrw.swenpr.bomberman.server.view.MainWindow;
import hrw.swenpr.bomberman.server.LogMessage;
import hrw.swenpr.bomberman.server.LogMessage.LEVEL;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Communication {
	
	/**
	 * Send a message to a specified socket
	 * 
	 * @param socket The socket to use
	 * @param message The message to send
	 */
	public <T> void sendToClient(Socket socket, T message) {
		ObjectOutputStream out;
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			MainWindow.log(new LogMessage(LEVEL.WARNING, "Unable to send message"));
		}
	}
	
	/**
	 * Sends a message to all clients registered by iterating over
	 * all ClientThreads and invoking sendToClient for each socket
	 * 
	 * @param message The message to send
	 */
	public <T> void sendToAllClients(T message){
		ArrayList<ClientThread> threads = Server.getModel().getClientThreads();
		Iterator<ClientThread> it = threads.iterator();
		
		while(it.hasNext()) {
			sendToClient(it.next().getSocket(), message);
		}
	}
}
