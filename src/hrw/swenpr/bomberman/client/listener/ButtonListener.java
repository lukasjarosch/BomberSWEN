package hrw.swenpr.bomberman.client.listener;

import hrw.swenpr.bomberman.client.MainClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	private MainClient mainClient;
	
	public ButtonListener(MainClient mainClient) 
	{
		this.mainClient = mainClient;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
