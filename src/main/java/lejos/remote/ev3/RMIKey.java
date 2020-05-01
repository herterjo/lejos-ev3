package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

import lejos.hardware.KeyListener;

public interface RMIKey extends Remote {
	
	int UP = 0;
	int ENTER = 1;
	int DOWN = 2;
	int RIGHT = 3;
	int LEFT = 4;
	int ESCAPE = 5;
	
	int getId() throws RemoteException;
	
	boolean isDown() throws RemoteException;
	
	boolean isUp() throws RemoteException;
	
	void waitForPress() throws RemoteException;
	
	void waitForPressAndRelease() throws RemoteException;
	
	void addKeyListener(KeyListener listener) throws RemoteException;
	
	void simulateEvent(int event) throws RemoteException;
}
