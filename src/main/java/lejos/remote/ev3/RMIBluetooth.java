package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import lejos.hardware.RemoteBTDevice;

public interface RMIBluetooth extends Remote {
	
	Collection<RemoteBTDevice> search() throws RemoteException;
	
	String getBluetoothAddress() throws RemoteException;
	
	boolean getVisibility() throws RemoteException;
	
	void setVisibility(boolean visible) throws RemoteException;

}
