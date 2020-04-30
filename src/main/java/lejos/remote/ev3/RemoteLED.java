package lejos.remote.ev3;

import java.rmi.RemoteException;
import java.util.concurrent.Future;

import lejos.hardware.LED;
import lejos.hardware.port.PortException;
import lejos.utility.ExceptionWrapper;

public class RemoteLED implements LED {
	private final RMILED led;
	
	public RemoteLED(RMILED led) {
		this.led=led;
	}
	
	@Override
	public Future<ExceptionWrapper> setPattern(int pattern) {
		try {
			return led.setPattern(pattern);
		} catch (RemoteException e) {
			throw new PortException(e);
		}
	}
}
