package hrw.swenpr.bomberman.client.listener;

import java.awt.Point;
import java.util.ArrayList;

import hrw.swenpr.bomberman.client.ClientModel;
import hrw.swenpr.bomberman.client.MainClient;
import hrw.swenpr.bomberman.common.BombEvent;
import hrw.swenpr.bomberman.common.BombermanBaseModel.FieldType;
import hrw.swenpr.bomberman.common.BombermanEvent;
import hrw.swenpr.bomberman.common.BombermanListener;

public class GameListener implements BombermanListener {

	@Override
	public void bombExplode(BombEvent event) {
		ArrayList[] explosion = event.getExplosion();
		ClientModel model = MainClient.getInstance().getModel();
		
		for(int i = 0; i < explosion.length; i++){
			for(Point p : (ArrayList<Point>)explosion[i]){
				
				try {
					switch (model.getFieldType(p)) {
					case INDESTRUCTUBLE_FIELD:
						//stop this direction and continue with next direction
						break;
					case DESTRUCTIBLE_FIELD:
						//delete panel and continue with next direction
						
						break;
					case USER1:
						
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
			}
		}
	}

	@Override
	public void modelChanged(BombermanEvent event) {		
	}

}

