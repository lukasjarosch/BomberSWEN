package hrw.swenpr.bomberman.server;

import java.io.File;
import java.util.ArrayList;

import hrw.swenpr.bomberman.common.BombermanBaseModel;
import hrw.swenpr.bomberman.common.rfc.User;

public class ServerModel extends BombermanBaseModel {

	public ServerModel(ArrayList<User> users, File level) {
		super(users, level);
	}

}
