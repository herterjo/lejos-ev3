package lejos.remote.ev3;

import java.rmi.RemoteException;
import java.util.concurrent.Future;

import lejos.hardware.motor.MotorRegulator;
import lejos.hardware.port.BasicMotorPort;
import lejos.hardware.port.PortException;
import lejos.hardware.port.TachoMotorPort;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

public class RemoteMotorPort extends RemoteIOPort implements TachoMotorPort {
	protected RMIMotorPort rmi;
	protected RMIEV3 rmiEV3;
	
	public RemoteMotorPort(RMIEV3 rmiEV3) {
		this.rmiEV3 = rmiEV3;
	}
	
	public boolean open(int typ, int portNum, RemotePort remotePort) {
        boolean res = super.open(typ,portNum,remotePort);
		try {
			rmi = rmiEV3.openMotorPort(getName());
		} catch (RemoteException e) {
			throw new PortException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return res;
	}
    /**
     * Low-level method to control a motor. 
     * 
     * @param power power from 0-100
     * @param mode defined in <code>BasicMotorPort</code>. 1=forward, 2=backward, 3=stop, 4=float.
     * @see BasicMotorPort#FORWARD
     * @see BasicMotorPort#BACKWARD
     * @see BasicMotorPort#FLOAT
     * @see BasicMotorPort#STOP
	 * @return
     */
    public Future<ExceptionWrapper> controlMotor(int power, int mode)
    {
    	return AsyncExecutor.execute(() -> {
			rmi.controlMotor(power, mode);
		});
    }


    /**
     * returns tachometer count
     * @return
     */
    public Future<ReturnWrapper<Integer>> getTachoCount()
    {
    	return AsyncExecutor.execute(() -> rmi.getTachoCount());
    }
    
    /**
     *resets the tachometer count to 0;
	 * @return
	 */
    public Future<ExceptionWrapper> resetTachoCount()
    {
    	return AsyncExecutor.execute(() -> rmi.resetTachoCount());
    }
    
    public void setPWMMode(int mode)
    {
    	try {
			rmi.setPWMMode(mode);
		} catch (RemoteException e) {
			throw new PortException(e);
		}
    }
    
    public void close() {
    	try {
			rmi.close();
		} catch (RemoteException e) {
			throw new PortException(e);
		}
    }

    @Override
    public MotorRegulator getRegulator()
    {
        // TODO Does it make sense to allow this to be remote?
        throw(new UnsupportedOperationException("Remote regulators are not supported"));
        //return null;
    }
}
