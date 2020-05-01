package lejos.remote.ev3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.TachoMotorPort;
import lejos.remote.ev3.RMIMotorPort;
import lejos.utility.AsyncExecutor;

public class RMIRemoteMotorPort extends UnicastRemoteObject implements RMIMotorPort {

	private static final long serialVersionUID = -5729213618672262271L;
	private final TachoMotorPort port;
	
	protected RMIRemoteMotorPort(String portName) throws Exception {
		super(0);
		port = LocalEV3.get().getPort(portName).open(TachoMotorPort.class);
	}

	@Override
	public void controlMotor(int power, int mode) throws RemoteException {
		System.out.println("Control mode, power: " + power + ", mode: " + mode);
		port.controlMotor(power, mode);
	}

	@Override
	public int getTachoCount() throws Exception{
		return port.getTachoCount().get().getValue();
	}

	@Override
	public void resetTachoCount() throws Exception {
		port.resetTachoCount();
	}

	@Override
	public void close() throws RemoteException {
		port.close();
	}

	@Override
	public void setPWMMode(int mode) throws RemoteException {
		port.setPWMMode(mode);
	}
}
