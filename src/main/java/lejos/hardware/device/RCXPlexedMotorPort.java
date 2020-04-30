package lejos.hardware.device;

import lejos.hardware.port.BasicMotorPort;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

/**
 * Supports a motor connected to the Mindsensors RCX Motor Multiplexer
 * 
 * @author Lawrie Griffiths
 *
 */
public class RCXPlexedMotorPort implements BasicMotorPort {
	private RCXMotorMultiplexer plex;
	private int id;
	
	public RCXPlexedMotorPort(RCXMotorMultiplexer plex, int id) {
		this.plex = plex;
		this.id = id;
	}
	
	public Future<ExceptionWrapper> controlMotor(int power, int mode) {
		int mmMode = mode;
		if (mmMode == BasicMotorPort.FLOAT) mmMode = 0; // float
		int mmPower = (int) (power * 2.55f);
		if (mmMode == BasicMotorPort.STOP) {
			mmPower = 255; // Maximum breaking
		}
		plex.sendCommand(id, mmMode, mmPower);
		return ExceptionWrapper.getCompletedException(null);
	}
	
	public void setPWMMode(int mode) {
		// Not implemented
	}

    @Override
    public void close()
    {
        // not implemented
    }

	@Override
	public Future<ExceptionWrapper> closeRet() {
		return AsyncExecutor.execute(this::close);
	}

	@Override
    public String getName()
    {
        return null;
    }

    @Override
    public boolean setPinMode(int mode)
    {
        return false;
    }
}
