package hrw.swenpr.bomberman.client;

import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage;
import hrw.swenpr.bomberman.common.rfc.ErrorMessage.ErrorType;
import hrw.swenpr.bomberman.common.rfc.Header;
import hrw.swenpr.bomberman.common.rfc.LevelAvailable;
import hrw.swenpr.bomberman.common.rfc.LevelFile;
import hrw.swenpr.bomberman.common.rfc.LevelSelection;
import hrw.swenpr.bomberman.common.rfc.LoginOk;
import hrw.swenpr.bomberman.common.rfc.TimeSelection;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserDead;
import hrw.swenpr.bomberman.common.rfc.UserPosition;
import hrw.swenpr.bomberman.common.rfc.UserRemove;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

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
	 * Reference to the mainClient
	 */
	private MainClient client;
	
	/**
	 * ObjectOutputStream
	 */
	private ObjectOutputStream out;
	
	/**
	 * Create separate thread.
	 * 
	 * @param socket the socket connected to the server 
	 */
	public Communication(Socket socket, MainClient client){
		this.socket = socket;
		this.client = client;
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public void run() {
		try {
			// get input stream
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object msg = null;

			// loop until thread gets interrupted
			while (!Thread.interrupted()) {
				msg = in.readObject();
				
				System.out.println("Received message of type: " + Header.getMessageType(msg));
				
				// determine the message
				switch (Header.getMessageType(msg)) {
				case LOGIN_OK:
					LoginOk login = (LoginOk) msg;
					
					client.setVisible(true);
					
					// when userID = 0 -> admin
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
					client.setLevel(((LevelSelection) msg).getFilename());
					break;
					
				case TIME_SELECTION:
					client.setTime(((TimeSelection) msg).getTime());
					break;
					
				case LEVEL_AVAILABLE:
					client.setAvailableLevel(((LevelAvailable) msg).getLevel());
					break;
							
				case LEVEL_FILE:
					File tmp = new File(System.getProperty("user.dir") + File.separator + client.getLevelName());
					((LevelFile) msg).writeLevelFile(tmp);
					client.getLevelFile(tmp);
					break;
					
				case GAME_START:
					client.startGame();
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
					client.getModel().movePlayer((UserPosition) msg);
					client.playerMoved((UserPosition) msg);
					break;
					
				case USER_DEAD:
					client.playerDead((UserDead) msg);
					break;
					
				case USER_REMOVE:
					client.removePlayer((UserRemove) msg);
					break;
					
				case BOMB:
					client.bombIsSet((Bomb) msg);
					break;
				
				case ERROR_MESSAGE:
					ErrorMessage error = (ErrorMessage) msg;
					
					JOptionPane.showMessageDialog(client, error.getMessage());
					
					// fatal error
					if(error.getSubtype() == ErrorType.ERROR) 
						System.exit(0);
					
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
			out.writeObject(message);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			System.out.println("ERROR: Could not send the message.");
		}
	}
}
