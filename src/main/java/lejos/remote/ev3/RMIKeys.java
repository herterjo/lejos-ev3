package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIKeys extends Remote {
	
	void discardEvents() throws RemoteException;
	
	int waitForAnyEvent() throws RemoteException;
	
	int waitForAnyEvent(int timeout) throws RemoteException;
	
	int waitForAnyPress(int timeout) throws RemoteException;
	
	int waitForAnyPress() throws RemoteException;
	
	int getButtons() throws RemoteException;
	
	int readButtons() throws RemoteException;
	
	void setKeyClickVolume(int vol) throws RemoteException;
	
	int getKeyClickVolume() throws RemoteException;
	
	void setKeyClickLength(int len) throws RemoteException;
	
	int getKeyClickLength() throws RemoteException;
	
	void setKeyClickTone(int key, int freq) throws RemoteException;
	
	int getKeyClickTone(int key) throws RemoteException;

}
