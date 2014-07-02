package hrw.swenpr.bomberman.client.listener;

import hrw.swenpr.bomberman.client.ClientModel;
import hrw.swenpr.bomberman.client.MainClient;
import hrw.swenpr.bomberman.common.BombEvent;
import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.BombermanBaseModel.FieldType;
import hrw.swenpr.bomberman.common.BombermanListener;
import hrw.swenpr.bomberman.common.UserModel;
import hrw.swenpr.bomberman.common.rfc.User;
import hrw.swenpr.bomberman.common.rfc.UserDead;

import java.awt.Point;
import java.util.ArrayList;

public class GameListener implements BombermanListener {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void bombExplode(BombEvent event) {
		ArrayList[] explosion = event.getExplosion();
		ClientModel model = MainClient.getInstance().getModel();
		UserModel usr = model.getUser(event.getUserID());
		MainClient main = MainClient.getInstance();
		boolean abort = false;
		
		for(int i = 0; i < explosion.length; i++){
			for(Point p : (ArrayList<Point>)explosion[i]){
				try {
					System.out.println(model.getFieldType(p));
					switch (model.getFieldType(p)) {
					case ITEM_MEGA_BOMB:
						//set panel type to normal walkable field
						model.setField(p, FieldType.PLAIN_FIELD);
						//delete special item
						model.collectSpecialItem(p);
						//redraw panel
						main.updatePanel(p);
						//stop this direction and continue with next direction
						abort = true;
						break;
					case ITEM_SUPER_BOMB:
						//set panel type to normal walkable field
						model.setField(p, FieldType.PLAIN_FIELD);
						//delete special item
						model.collectSpecialItem(p);
						//redraw panel
						main.updatePanel(p);
						//stop this direction and continue with next direction
						abort = true;
						break;
					case INDESTRUCTUBLE_FIELD:
						//stop this direction and continue with next direction
						abort = true;
						break;
					case DESTRUCTIBLE_FIELD:
						//delete panel and continue with next direction
						model.setField(p, model.getSpecialItem(p));
						main.updatePanel(p);
						
						abort = true;
						break;
					case USER1:
						//Check if hit user is user who set bomb
						if(BombermanBaseModel.convertToUserID(FieldType.USER1) == event.getUserID()){
							 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() - 5);
						}else{
							//User killed other player with bomb and gets points for it
							usr = model.getUser(event.getUserID()); 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() + 10);
						}
						//Update the user table in the sidebar
						main.getSidebar().updateTable(new User(usr.getUserID(), usr.getUsername(), usr.getScore(), usr.getColor()));
							
						//Redraw the panel
						model.setField(p, FieldType.PLAIN_FIELD);
						main.updatePanel(p);
						//Send a user dead message to the server
						main.getCommunication().sendMessage(new UserDead(BombermanBaseModel.convertToUserID(FieldType.USER1)));
						//check if killed user is player
						if(BombermanBaseModel.convertToUserID(FieldType.USER1) == main.getUserID())
							main.setDead(true);
						break;
					case USER2:
						//Check if hit user is user who set bomb
						if(BombermanBaseModel.convertToUserID(FieldType.USER2) == event.getUserID()){
							usr = model.getUser(event.getUserID()); 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() - 5);
						}else{
							//User killed other player with bomb and gets points for it
							usr = model.getUser(event.getUserID()); 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() + 10);
						}
						//Update the user table in the sidebar
						main.getSidebar().updateTable(new User(usr.getUserID(), usr.getUsername(), usr.getScore(), usr.getColor()));
						//Redraw the panel
						model.setField(p, FieldType.PLAIN_FIELD);
						main.updatePanel(p);
						//Send a user dead message to the server
						main.getCommunication().sendMessage(new UserDead(BombermanBaseModel.convertToUserID(FieldType.USER2)));
						//check if killed user is player
						if(BombermanBaseModel.convertToUserID(FieldType.USER2) == main.getUserID())
							main.setDead(true);
						break;
					case USER3:
						//Check if hit user is user who set bomb
						if(BombermanBaseModel.convertToUserID(FieldType.USER3) == event.getUserID()){
							usr = model.getUser(event.getUserID());
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() - 5);
						}else{
							//User killed other player with bomb and gets points for it
							usr = model.getUser(event.getUserID()); 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() + 10);
						}
						//Update the user table in the sidebar
						main.getSidebar().updateTable(new User(usr.getUserID(), usr.getUsername(), usr.getScore(), usr.getColor()));
						//Redraw the panel
						model.setField(p, FieldType.PLAIN_FIELD);
						main.updatePanel(p);
						//Send a user dead message to the server
						main.getCommunication().sendMessage(new UserDead(BombermanBaseModel.convertToUserID(FieldType.USER3)));
						//check if killed user is player
						if(BombermanBaseModel.convertToUserID(FieldType.USER3) == main.getUserID())
							main.setDead(true);
						break;
					case USER4:
						//Check if hit user is user who set bomb
						if(BombermanBaseModel.convertToUserID(FieldType.USER4) == event.getUserID()){
							usr = model.getUser(event.getUserID()); 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() - 5);
						}else{
							//User killed other player with bomb and gets points for it
							usr = model.getUser(event.getUserID()); 
							//reduce user points because killed by own bomb
							usr.setScore(usr.getScore() + 10);
						}
						//Update the user table in the sidebar
						main.getSidebar().updateTable(new User(usr.getUserID(), usr.getUsername(), usr.getScore(), usr.getColor()));
						//Redraw the panel
						model.setField(p, FieldType.PLAIN_FIELD);
						main.updatePanel(p);
						//Send a user dead message to the server
						main.getCommunication().sendMessage(new UserDead(BombermanBaseModel.convertToUserID(FieldType.USER4)));
						//check if killed user is player
						if(BombermanBaseModel.convertToUserID(FieldType.USER4) == main.getUserID())
							main.setDead(true);
						break;

					default:
						break;
					}
				} catch (Exception e) {
					
				}
				if(abort){
					abort = false;
					//Delete exploded bomb from the pit and redraw position
					model.setField(event.getPosition(), FieldType.PLAIN_FIELD);
					main.updatePanel(event.getPosition());
					break;
				}
				//Delete exploded bomb from the pit and redraw position
				model.setField(event.getPosition(), FieldType.PLAIN_FIELD);
				main.updatePanel(event.getPosition());
			}
		}
	}
}

