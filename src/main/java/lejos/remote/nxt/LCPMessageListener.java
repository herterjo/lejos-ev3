package lejos.remote.nxt;

public interface LCPMessageListener {
	
	void messageReceived(byte inBox, String message);
}
