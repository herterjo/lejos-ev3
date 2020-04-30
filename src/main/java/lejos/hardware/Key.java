package lejos.hardware;

public interface Key {
	
	int UP = 0;
	int ENTER = 1;
	int DOWN = 2;
	int RIGHT = 3;
	int LEFT = 4;
	int ESCAPE = 5;
	
	int KEY_RELEASED = 0;
	int KEY_PRESSED = 1;
	int KEY_PRESSED_AND_RELEASED = 2;
	
	int getId();
	
	boolean isDown();
	
	boolean isUp();
	
	void waitForPress();
	
	void waitForPressAndRelease();
	
	void addKeyListener(KeyListener listener);
	
	void simulateEvent(int event);
	
	String getName();

}
