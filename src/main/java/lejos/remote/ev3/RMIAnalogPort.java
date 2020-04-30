package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIAnalogPort extends Remote {
	
	float getPin6() throws RemoteException;

	float getPin1() throws RemoteException;
	
	boolean setPinMode(int mode) throws RemoteException;
	
	void close() throws RemoteException;

    void getFloats(float[] vals, int offset, int length) throws RemoteException;

}
