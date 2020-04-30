package lejos.robotics.mapping;

import lejos.robotics.mapping.NavigationModel.NavEvent;

public interface NavEventListener {
	void whenConnected();
	
	void eventReceived(NavEvent navEvent);
}
