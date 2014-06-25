package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.LogMessage.LEVEL;
import hrw.swenpr.bomberman.server.thread.ClientThread;
import hrw.swenpr.bomberman.server.view.MainWindow;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Communication {
	
	/**
	 * Send a message to a specified socket
	 * 
	 * @param socket The output stream to use
	 * @param message The message to send
	 * 
	 * @author Lukas Jarosch
	 */
	public <T> void sendToClient(ObjectOutputStream out, T message) {
		try {
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
	 * 
	 * @author Lukas Jarosch
	 */
	public <T> void sendToAllClients(T message){
		ArrayList<ClientThread> threads = Server.getModel().getClientThreads();
		
		for (ClientThread clientThread : threads) {
			sendToClient(clientThread.getOutputStream(), message);
		}
	}
	
	
	/**
	 * <p>Sends a message to all clients, except the {@code exceptThread}, by iterating
	 * over all {@link ClientThread}s and invoking {@code sendToClient()} for each socket.
	 * <p>Can be used to forward a message to all other clients.
	 * 
	 * @param message the message to send
	 * @param exceptThread the {@link ClientThread} to be excluded from the "broadcast"
	 * 
	 * @author Marco Egger
	 */
	public <T> void sentToAllOtherClients(T message, ClientThread exceptThread) {
		ArrayList<ClientThread> threads = Server.getModel().getClientThreads();

		for (ClientThread clientThread : threads) {
			// only send if exceptThread does not match
			if(!clientThread.equals(exceptThread))
				sendToClient(clientThread.getOutputStream(), message);
		}
	}
}
