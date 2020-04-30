package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMISampleProvider extends Remote {
	float[] fetchSample() throws Exception;
	
	void close() throws RemoteException;
}
