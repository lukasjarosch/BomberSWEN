package hrw.swenpr.bomberman.client.listener;

import java.awt.Point;
import java.util.ArrayList;

import hrw.swenpr.bomberman.client.ClientModel;
import hrw.swenpr.bomberman.client.MainClient;
import hrw.swenpr.bomberman.common.BombEvent;
import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.BombermanBaseModel.FieldType;
import hrw.swenpr.bomberman.common.BombermanEvent;
import hrw.swenpr.bomberman.common.BombermanListener;

public class GameListener implements BombermanListener {

	@Override
	public void bombExplode(BombEvent event) {
		ArrayList[] explosion = event.getExplosion();
		ClientModel model = MainClient.getInstance().getModel();
		boolean abort = false;
		
		for(int i = 0; i < explosion.length; i++){
			for(Point p : (ArrayList<Point>)explosion[i]){
				
				try {
					switch (model.getFieldType(p)) {
					case INDESTRUCTUBLE_FIELD:
						//stop this direction and continue with next direction
						abort = true;
						break;
					case DESTRUCTIBLE_FIELD:
						//delete panel and continue with next direction
						model.setField(p, model.getSpecialItem(p));						
						abort = true;
						break;
					case USER1:
						//Check if hit user is user who set bomb
						if(BombermanBaseModel.convertToUserID(FieldType.USER1) == event.getUserID()){
							UserModel usr = model.getUser(event.getUserID()); 
							usr.setScore(usr.getScore() - 5);
						}
						
						break;
					case USER2:
						
						break;
					case USER3:
						
						break;
					case USER4:
						
						break;

					default:
						break;
					}
				} catch (Exception e) {
					
				}
				if(abort){
					abort = true;
					break;
				}
			}
		}
	}
}

