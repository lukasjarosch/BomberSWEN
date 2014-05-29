package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage.ErrorType;
import hrw.swenpr.bomberman.common.rfc.Header;
import hrw.swenpr.bomberman.common.rfc.LevelAvailable;
import hrw.swenpr.bomberman.common.rfc.LoginOk;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * In this class the communication with the server is done.
 * 
 * @author Daniel Hofer
 * @author Marco Egger
 * 
 */
public class Communication extends Thread {
	/**
	 * The socket connected to the server
	 */
	private Socket socket;

	/**
	 * Create separate thread.
	 * 
	 * @param socket the socket connected to the server
	 */
	public Communication(Socket socket) {
		this.socket = socket;
	}

	
	@Override
	public void run() {
		try {
			// get input stream
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object msg = null;
			MainClient client = MainClient.getInstance();

			// loop until thread gets interrupted
			while (!Thread.interrupted()) {
				msg = in.readObject();
				
				// determine the message
				switch (Header.getMessageType(msg)) {
				case LOGIN_OK:
					LoginOk login = (LoginOk) msg;
					
					client.setVisible(true);
					
					// when userID = 0 -> game master
					if (login.getUserID() == 0) {
						client.setAdmin(true);
					}
					
					client.setUserID(login.getUserID());
					break;
					
				case USER:
					User user = (User) msg;
					client.addPlayer(user);
					client.getSidebar().updateTable(user);
					break;
					
				case LEVEL_SELECTION:
					
					break;
					
				case LEVEL_AVAILABLE:
					client.setAvailableLevel(((LevelAvailable) msg).getLevel());
					break;
							
				case LEVEL_FILE:
					
					break;
					
				case GAME_START:
					client.gameStart();
					break;
					
				case ROUND_START:
					client.roundStart();
					break;
					
				case ROUND_FINISHED:
					client.roundFinish();
					break;
					
				case GAME_OVER:
					
					break;
	
				case USER_POSITION:
					client.movePlayer((UserPosition) msg);
					break;
					
				case USER_DEAD:
					client.playerDead((User) msg);
					break;
					
				case BOMB:
					Bomb bomb = (Bomb) msg;
					client.setBomb(bomb);
					break;
					
				case OBJECT_DESTROYED:
					
					break;
				
				case ERROR_MESSAGE:
					ErrorMessage error = (ErrorMessage) msg;
					
					// fatal error
					if(error.getSubtype() == ErrorType.ERROR) {
						System.out.println("Error received: " + error.getMessage());
					}
					else {
						System.out.println("Warning received: " + error.getMessage());
					}
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR: Failed to receive message.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("ERROR: Received class not found.");
		}
	}

	/**
	 * Sends a message to the server.
	 * 
	 * @param message the message to send
	 */
	public void sendMessage(Object message) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			System.out.println("ERROR: Could not send the message.");
		}
	}
}
