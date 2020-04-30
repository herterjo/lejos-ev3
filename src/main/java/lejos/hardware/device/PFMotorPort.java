package lejos.hardware.device;

import lejos.hardware.port.BasicMotorPort;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

/**
 * MotorPort for PF Motors using HiTechnic IRLink
 * 
 * @author Lawrie Griffiths
 *
 */
public class PFMotorPort implements BasicMotorPort {
	private int channel, slot;
	private IRLink link;
	private static final int[] modeTranslation = {1,2,3,0};
	
	public PFMotorPort(IRLink link, int channel, int slot) {
		this.channel = channel;
		this.slot = slot;
		this.link = link;
	}
	
	public Future<ExceptionWrapper> controlMotor(int power, int mode) {
		if (mode < 1 || mode > 4)
		    return ExceptionWrapper.getCompletedException(null);
		link.sendPFComboDirect(channel, (slot == 0 ? modeTranslation[mode-1] : 0), (slot == 1 ? modeTranslation[mode-1] : 0));
        return ExceptionWrapper.getCompletedException(null);
	}

	public void setPWMMode(int mode) {
		// Not implemented
	}

    @Override
    public void close()
    {
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
