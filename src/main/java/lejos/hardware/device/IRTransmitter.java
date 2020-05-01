package lejos.hardware.device;

/**
 * Interface for infra-red transmitters that can send bytes to an RCX
 * 
 * @author Lawrie Griffiths
 */
public interface IRTransmitter {
	
	/**
	 * Send raw bytes to the RCX
	 * @param data the raw data
	 * @param len the number of bytes
	 */
    void sendBytes(byte[] data, int len);
	
	/**
	 * Send a packet of data to the RCX
	 * @param packet
	 */
    void sendPacket(byte[] packet);
	
	/**
	 * Send a remote control command to the RCX
	 * 
	 * @param msg the code for the remote command
	 */
    void sendRemoteCommand(int msg);
	
	void runProgram(int programNumber);
	
	void forwardStep(int motor);
	
	void backwardStep(int motor);
	
	void beep();
	
	void stopAllPrograms();
	
}
