package lejos.hardware;

public interface Keys {
	
	int ID_UP = 0x1;
	int ID_ENTER = 0x2;
	int ID_DOWN = 0x4;
	int ID_RIGHT = 0x8;
	int ID_LEFT = 0x10;
	int ID_ESCAPE = 0x20;
	int ID_ALL = 0x3f;
	
	int NUM_KEYS = 6;
	
	String VOL_SETTING = "lejos.keyclick_volume";
	String LEN_SETTING = "lejos.keyclick_length";
	String FREQ_SETTING = "lejos.keyclick_frequency";

	void discardEvents();
	
	int waitForAnyEvent();
	
	int waitForAnyEvent(int timeout);
	
	int waitForAnyPress(int timeout);
	
	int waitForAnyPress();
	
	int getButtons();
	
	int readButtons();
	
	void setKeyClickVolume(int vol);
	
	int getKeyClickVolume();
	
	void setKeyClickLength(int len);
	
	int getKeyClickLength();
	
	void setKeyClickTone(int key, int freq);
	
	int getKeyClickTone(int key);
	
}
