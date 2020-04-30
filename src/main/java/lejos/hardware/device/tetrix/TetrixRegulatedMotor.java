package lejos.hardware.device.tetrix;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/** 
 * Tetrix DC motor abstraction with encoder support that implements <code>RegulatedMotor</code>. The Tetrix motor must have an 
 * encoder installed and connected to
 * the controller for the methods in this class to work. If an encoder is not installed, use the <code>{@link TetrixMotor}</code>
 * class instead.
 * <p>Use <code>{@link TetrixMotorController#getRegulatedMotor}</code> to retrieve a <code>TetrixRegulatedMotor</code> instance.
 * 
 * @author Kirk P. Thompson
 */
public class TetrixRegulatedMotor extends TetrixEncoderMotor implements RegulatedMotor{
    static final int LISTENERSTATE_STOP = 0;
    static final int LISTENERSTATE_START = 1;
    
    RegulatedMotorListener listener;
    
    TetrixRegulatedMotor(TetrixMotorController mc, int channel) throws Exception {
    	super(mc, channel);
        super.setRegulate(true);
    }

    /**
     * OVERRIDDEN TO NOT ALLOW CHANGE OF REGULATED STATE as the <code>TetrixRegulatedMotor</code> class must use regulation.
     * Motors are always in
     * regulated mode when using the <code>TetrixRegulatedMotor</code> class.
     * 
     * @param regulate Ignored
     */
    @Override
    public void setRegulate(boolean regulate) {
        // ignore and don't allow change of regulation
    }
    
    @SuppressWarnings("hiding")
	public void addListener(RegulatedMotorListener listener) {
        this.listener = listener;
    }
    
    void doListenerState(int listenerState) throws Exception {
        if (this.listener == null) return;
        if (listenerState == LISTENERSTATE_STOP) {
        	// wait for a complete stop before notifying
        	new Thread(() -> {
                waitComplete();
                try {
                    listener.rotationStopped(TetrixRegulatedMotor.this, getTachoCount().get().getValue(), false, System.currentTimeMillis());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } else {
            this.listener.rotationStarted(this, getTachoCount().get().getValue(), false, System.currentTimeMillis());
        }
    }
    
    public RegulatedMotorListener removeListener() {
        RegulatedMotorListener old = this.listener;
        this.listener = null;
        return old;
    }
    
    /**
     * Return the current rotational speed calculated from the encoder position every 100 ms. This will
     * likely differ from what was specified in <code>setSpeed</code>.
     * 
     * @return The current rotational speed in deg/sec
     */
    public Future<ReturnWrapper<Integer>> getRotationSpeed() throws Exception {
        return ReturnWrapper.getCompletedReturnNormal(Math.round(.01f * mc.doCommand(TetrixMotorController.CMD_GETSPEED, 0, channel)));
    }
    
    public void stop(boolean immediateReturn) throws Exception {
        super.stop();
        if (!immediateReturn) waitComplete();
    }

    public void flt(boolean immediateReturn) throws Exception {
        super.flt();
        if (!immediateReturn) waitComplete();
    }

    public Future<ExceptionWrapper> waitComplete() {
    	return super.waitRotateComplete();
    }
    
    /**
     * Rotate by the requested number of degrees while blocking until completion.
     * 
     * @param angle number of degrees to rotate relative to the current position.
     */
    public void rotate(int angle) throws Exception {
        rotate(angle, false);
    }
    
    /**
     * Rotate to the target angle while blocking until completion.
     * 
     * @param limitAngle Angle [in degrees] to rotate to.
     */
    public void rotateTo(int limitAngle) throws Exception {
        rotateTo(limitAngle, false);
    }
    
     /**
      * Sets desired motor speed, in degrees per second. Since the TETRIX Motor Controller only has power adjustment,
      * the power value is derived from the passed <code>speed</code> value as:
      * <br>
      * <pre>  power = Math.round((speed - 0.5553f) * 0.102247398f);</pre>
      * and as such, the actual speed value will not be exact.
      * <p>
      * The maximum reliably sustainable velocity for the TETRIX DC Gear motor P/N 739023 (which was used as the 
      * test case for creating this class) is 154 RPM which results in 924 degs/sec.
      * 
      * @param speed value in degrees/sec
      * @see #getSpeed
      */
    public void setSpeed(int speed) throws Exception {
        // experimental data gives: speed = 9.7802 * power + 0.5553
        int power = Math.round((Math.abs(speed) - 0.5553f) * 0.102247398f);
        super.setPower(power);
        
    }

    /**
     * Return the speed value calculated from the actual power value as:
     * <br>
     * <pre>  speed = Math.round(9.7802f * super.getPower() + 0.5553f);</pre>
     * and as such, the actual speed value may not be what was set with <code>setSpeed</code>.
     * 
     * @return The speed value (converted from power value) in degrees/sec
     * @see #setSpeed
     */
    public int getSpeed() throws Exception {
        int speed = Math.round(9.7802f * super.getPower() + 0.5553f);
        return speed;
    }
    
    /**
     * NOT IMPLEMENTED as the TEXTRIX motor controller does not support this command.
     * @return Always 924 degrees/sec
     */
    public float getMaxSpeed() {
        return 924f;
    }
    
    /**
     * NOT IMPLEMENTED as the TEXTRIX motor controller does not support this command.
     * @return Always <code>false</code>
     */
    public Future<ReturnWrapper<Boolean>> isStalled() {
        return ReturnWrapper.getCompletedReturnNormal(false);
    }
    
    /**
     * NOT IMPLEMENTED as the TEXTRIX motor controller does not support this command.
     */
    public void setStallThreshold(int error, int time) {
    	// do nothing
    }
    
    /**
     * NOT IMPLEMENTED as the TEXTRIX motor controller does not support this command.
     * @param acceleration Ignored
     */
    public void setAcceleration(int acceleration) {
    	// do nothing
    }

	@Override
	public void close() {
		// Do nothing
	}

    @Override
    public void synchronizeWith(RegulatedMotor[] syncList)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startSynchronization()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endSynchronization()
    {
        // TODO Auto-generated method stub
        
    }
}
