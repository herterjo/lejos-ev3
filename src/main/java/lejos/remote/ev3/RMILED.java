package lejos.remote.ev3;

import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Future;

public interface RMILED extends Remote {
	
	Future<ExceptionWrapper> setPattern(int pattern) throws RemoteException;

}
