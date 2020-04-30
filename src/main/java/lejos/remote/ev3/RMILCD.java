package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMILCD extends Remote {
	
	void drawChar(char c, int x, int y) throws RemoteException;
	
	void clearDisplay() throws RemoteException;
	
	void drawString(String str, int x, int y, boolean inverted) throws RemoteException;
	 
	void drawString(String str, int x, int y) throws RemoteException;
	 
	void drawInt(int i, int x, int y) throws RemoteException;
	 
	void drawInt(int i, int places, int x, int y) throws RemoteException;

}
