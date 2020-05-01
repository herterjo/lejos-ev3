package lejos.robotics;

import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/**
 * <p>This class returns a motor that rotates in the reverse direction of a regular motor. All tachometer
 * readings are also reversed.</p> 
 * 
 * <p>Use the factory method MirrorMotor.invertMotor(RegulatedMotor) to retrieve an inverted motor.</p>
 * 
 * @author BB
 *
 */
public class MirrorMotor implements RegulatedMotor, RegulatedMotorListener {
	
	private final RegulatedMotor regMotor;
	private RegulatedMotorListener regListener;
	
	/**
	 * Returns an inverted RegulatedMotor.
	 * @param motor A RegulatedMotor, such as Motor.A.
	 * @return An inverted RegulatedMotor.
	 */
	public static RegulatedMotor invertMotor(RegulatedMotor motor) {
		if(motor instanceof MirrorMotor) {
			((MirrorMotor) motor).regMotor.addListener(((MirrorMotor) motor).regListener);
			return ((MirrorMotor) motor).regMotor;
		} 
		return new MirrorMotor(motor);
	}
	
	/**
	 * Returns an inverted EncoderMotor.
	 * @param motor An EncoderMotor, such as NXTMotor.
	 * @return An inverted EncoderMotor.
	 */
	public static EncoderMotor invertMotor(EncoderMotor motor) {
		if(motor instanceof ReversedEncoderMotor) {
			return ((ReversedEncoderMotor) motor).encoderMotor;
		}
		return new ReversedEncoderMotor(motor);
	}
						
	private MirrorMotor(RegulatedMotor motor) {
		// Make motor listener this regListener
		regListener = motor.removeListener(); // OK if listener is null
		// Need to add this to motor listener
		motor.addListener(this);
		this.regMotor = motor;
	}
	
	public void addListener(RegulatedMotorListener listener) {
		// Listener needs to be reversed too.
		regMotor.addListener(this);
		this.regListener = listener;
	}
	
	public RegulatedMotorListener removeListener() {
		RegulatedMotorListener old = regListener;
		regListener = null;
		regMotor.removeListener();
		return old;
	}

	public void flt(boolean immediateReturn) throws Exception {
		regMotor.flt(immediateReturn);
	}

	public int getLimitAngle() throws Exception {
		return -regMotor.getLimitAngle();// REVERSED
	}

	public float getMaxSpeed() {
		return regMotor.getMaxSpeed();
	}

	public int getSpeed() throws Exception {
		return regMotor.getSpeed();
	}

	public Future<ReturnWrapper<Boolean>> isStalled() {
		return regMotor.isStalled();
	}

	public void rotate(int angle) throws Exception {
		this.rotate(angle, false);
	}

	public Future<ExceptionWrapper> rotate(int angle, boolean immediateReturn) throws Exception {
		return regMotor.rotate(-angle, immediateReturn);// REVERSED
	}

	public void rotateTo(int angle) throws Exception {
		this.rotateTo(angle, false);
	}

	public void rotateTo(int angle, boolean immediateReturn) throws Exception {
		regMotor.rotateTo(-angle, immediateReturn);// REVERSED
	}

	public void setAcceleration(int acceleration) {
		regMotor.setAcceleration(acceleration);
	}

	public void setSpeed(int speed) throws Exception {
		regMotor.setSpeed(speed);
	}

	public void setStallThreshold(int error, int time) {
		regMotor.setStallThreshold(error, time);
	}

	public void stop(boolean immediateReturn) throws Exception {
		regMotor.stop(immediateReturn);
	}

	public Future<ExceptionWrapper> waitComplete() {
		return regMotor.waitComplete();
	}

	public void backward() throws Exception {
		regMotor.forward();// REVERSED
	}

	public void flt() throws Exception {
		this.flt(false);
	}

	public void forward() throws Exception {
		regMotor.backward(); // REVERSED
	}

	public Future<ReturnWrapper<Boolean>> isMoving() throws Exception {
		return regMotor.isMoving();
	}

	public void stop() throws Exception {
		regMotor.stop(false);
	}

	public Future<ReturnWrapper<Integer>> getRotationSpeed() throws Exception {
		return regMotor.getRotationSpeed();
	}

	public Future<ReturnWrapper<Integer>> getTachoCount() {
		return AsyncExecutor.execute(() -> -regMotor.getTachoCount().get().getValue());// REVERSED
	}

	public Future<ExceptionWrapper> resetTachoCount() throws Exception {
		return regMotor.resetTachoCount();
	}

	public void rotationStarted(RegulatedMotor motor, int tachoCount,
			boolean stalled, long timeStamp) {
		if(regListener!=null)
			regListener.rotationStarted(this, -tachoCount, stalled, timeStamp);
	}

	public void rotationStopped(RegulatedMotor motor, int tachoCount,
			boolean stalled, long timeStamp) throws Exception {
		if(regListener!=null)
				regListener.rotationStopped(this, -tachoCount, stalled, timeStamp);
	}

	@Override
	public void close() {
		regMotor.close();
	}

    @Override
    public void synchronizeWith(RegulatedMotor[] syncList)
    {
        regMotor.synchronizeWith(syncList);
        
    }

    @Override
    public void startSynchronization()
    {
        regMotor.startSynchronization();
        
    }

    @Override
    public void endSynchronization()
    {
        regMotor.endSynchronization();
        
    }
}