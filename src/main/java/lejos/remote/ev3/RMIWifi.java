package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIWifi extends Remote {
	
	String[] getAccessPointNames() throws RemoteException;
	
	String getAccessPoint() throws RemoteException;

}
