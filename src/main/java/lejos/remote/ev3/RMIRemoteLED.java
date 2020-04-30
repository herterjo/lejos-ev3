package lejos.remote.ev3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Future;

import lejos.hardware.LED;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

public class RMIRemoteLED extends UnicastRemoteObject implements RMILED  {
	private static final long serialVersionUID = -660643720408840563L;
	private LED led = LocalEV3.get().getLED();

	protected RMIRemoteLED() throws RemoteException {
		super(0);
	}

	@Override
	public Future<ExceptionWrapper> setPattern(int pattern) throws RemoteException {
		return led.setPattern(pattern);
	}
}
