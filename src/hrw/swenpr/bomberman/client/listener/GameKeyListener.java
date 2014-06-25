package hrw.swenpr.bomberman.client.listener;

import hrw.swenpr.bomberman.client.MainClient;
import hrw.swenpr.bomberman.common.rfc.Bomb;
import hrw.swenpr.bomberman.common.rfc.UserPosition;
import hrw.swenpr.bomberman.common.rfc.Bomb.BombType;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener 
{
	private MainClient mainClient;
	
	public GameKeyListener(MainClient mainClient) {
		this.mainClient = mainClient;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		// get the current position of the local player
		Point curPos = getCurrentPosition();
		Point newPos = null;
		
		switch (event.getKeyCode()) {
		// move upwards
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			newPos = new Point(curPos.x, curPos.y - 1);
			break;

		// move to the right
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			newPos = new Point(curPos.x + 1, curPos.y);
			break;

		// move downwards
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			newPos = new Point(curPos.x, curPos.y + 1);
			break;

		// move to the left
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			newPos = new Point(curPos.x - 1, curPos.y);
			break;
			
		// place a bomb
		case KeyEvent.VK_SPACE:
			mainClient.setBomb(new Bomb(mainClient.getUserID(), BombType.NORMAL_BOMB, getCurrentPosition()));
			break;
		
		// place a super-bomb
		case KeyEvent.VK_N:
			mainClient.setBomb(new Bomb(mainClient.getUserID(), BombType.SUPER_BOMB, getCurrentPosition()));
			break;
						
		// place a mega-bomb
		case KeyEvent.VK_M:
			mainClient.setBomb(new Bomb(mainClient.getUserID(), BombType.SUPER_BOMB, getCurrentPosition()));
			break;

		default:
			break;
		}
		
		// when movement key pressed and new position was calculated
		if(newPos != null)
			mainClient.movePlayer(new UserPosition(mainClient.getUserID(), newPos));
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * @return the current position of the local player
	 */
	private Point getCurrentPosition() {
		return mainClient.getModel().getUserPosition(mainClient.getUserID());
	}

}
