package lejos.remote.ev3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Future;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

public class RemoteRequestRegulatedMotor implements RegulatedMotor {
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private int portNum;

	public RemoteRequestRegulatedMotor(ObjectInputStream is,
			ObjectOutputStream os, String portName, char motorType) {
		this.is = is;
		this.os = os;
		portNum = portName.charAt(0) - 'A';
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.CREATE_REGULATED_MOTOR;
		req.str = portName;
		req.ch = motorType;
		sendRequest(req, false);
	}

	@Override
	public void forward() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_FORWARD;
		sendRequest(req, false);
	}

	@Override
	public void backward() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_BACKWARD;
		sendRequest(req, false);	
	}

	@Override
	public void stop() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_STOP;
		sendRequest(req, true);
	}

	@Override
	public void flt() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_FLT;
		sendRequest(req, true);
	}

	@Override
	public Future<ReturnWrapper<Boolean>> isMoving() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_IS_MOVING;
		return AsyncExecutor.execute(() -> sendRequest(req, true).result);
	}

	@Override
	public Future<ReturnWrapper<Integer>> getRotationSpeed() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_GET_ROTATION_SPEED;
		return AsyncExecutor.execute(() -> sendRequest(req, true).reply);
	}

	@Override
	public Future<ReturnWrapper<Integer>> getTachoCount() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_GET_TACHO_COUNT;
		return AsyncExecutor.execute(() -> sendRequest(req, true).reply);
	}

	@Override
	public Future<ExceptionWrapper> resetTachoCount() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_RESET_TACHO_COUNT;;
		sendRequest(req, false);
		return ExceptionWrapper.getCompletedException(null);
	}

	@Override
	public void addListener(RegulatedMotorListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public RegulatedMotorListener removeListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop(boolean immediateReturn) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_STOP_IMMEDIATE;
		req.flag = immediateReturn;
		sendRequest(req, !immediateReturn);
	}

	@Override
	public void flt(boolean immediateReturn) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_FLT_IMMEDIATE;
		req.flag = immediateReturn;
		sendRequest(req, !immediateReturn);
	}

	@Override
	public Future<ExceptionWrapper> waitComplete() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_WAIT_COMPLETE;
		sendRequest(req, true);
		return ExceptionWrapper.getCompletedException(null);
	}

	@Override
	public Future<ExceptionWrapper> rotate(int angle, boolean immediateReturn) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_ROTATE_IMMEDIATE;
		req.intValue2 = angle;
		req.flag = immediateReturn;
		sendRequest(req, !immediateReturn);
		return ExceptionWrapper.getCompletedException(null);
	}

	@Override
	public void rotate(int angle) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_ROTATE;
		req.intValue2 = angle;
		sendRequest(req, true);
	}

	@Override
	public void rotateTo(int limitAngle) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_ROTATE_TO;
		req.intValue2 = limitAngle;
		sendRequest(req, true);
	}

	@Override
	public void rotateTo(int limitAngle, boolean immediateReturn) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_ROTATE_TO_IMMEDIATE;
		req.intValue2 = limitAngle;
		req.flag = immediateReturn;
		sendRequest(req, !immediateReturn);	
	}

	@Override
	public int getLimitAngle() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_GET_LIMIT_ANGLE;
		return sendRequest(req, true).reply;
	}

	@Override
	public void setSpeed(int speed) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_SET_SPEED;
		req.intValue2 = speed;
		sendRequest(req, false);
	}

	@Override
	public int getSpeed() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_GET_SPEED;
		return sendRequest(req, true).reply;
	}

	@Override
	public float getMaxSpeed() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_GET_MAX_SPEED;
		return sendRequest(req, true).floatReply;
	}

	@Override
	public Future<ReturnWrapper<Boolean>> isStalled() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_IS_STALLED;
		return AsyncExecutor.execute(() -> sendRequest(req, true).result);
	}

	@Override
	public void setStallThreshold(int error, int time) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_SET_STALL_THRESHOLD;
		req.intValue2 = error;
		req.intValue3 = time;
		sendRequest(req, false);
	}

	@Override
	public void setAcceleration(int acceleration) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_SET_ACCELERATION;
		req.intValue2 = acceleration;
		sendRequest(req, false);
	}

	@Override
	public void close() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.MOTOR_CLOSE;
		sendRequest(req, true);
	}
	
	private EV3Reply sendRequest(EV3Request req, boolean replyRequired) {
		EV3Reply reply = null;
		req.replyRequired = replyRequired;
		req.intValue = portNum;
		try {
			os.reset();
			os.writeObject(req);
			if (replyRequired) {
				reply = (EV3Reply) is.readObject();
				if (reply.e != null) throw new RemoteRequestException(reply.e);
			}
			return reply;
		} catch (Exception e) {
			throw new RemoteRequestException(e);
		}
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
