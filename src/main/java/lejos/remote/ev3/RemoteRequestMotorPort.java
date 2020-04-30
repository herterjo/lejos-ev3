package lejos.remote.ev3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Future;

import lejos.hardware.motor.MotorRegulator;
import lejos.hardware.port.TachoMotorPort;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

public class RemoteRequestMotorPort extends RemoteRequestIOPort implements TachoMotorPort {
	private final ObjectInputStream is;
	private final ObjectOutputStream os;
	private int portNum;

	public RemoteRequestMotorPort(ObjectInputStream is, ObjectOutputStream os) {
		this.is = is;
		this.os = os;
	}
	
	@Override
	public boolean open(int typ, int portNum,
			RemoteRequestPort remoteRequestPort) {
		boolean res = super.open(typ,portNum,remoteRequestPort);
		this.portNum = portNum;
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.OPEN_MOTOR_PORT;
		req.intValue2 = typ;
		sendRequest(req, false);
		return res;
	}
	
	@Override
	public void close() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.CLOSE_MOTOR_PORT;
		sendRequest(req, false);	
	}

	@Override
	public Future<ExceptionWrapper> controlMotor(int power, int mode) {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.CONTROL_MOTOR;
		req.intValue2 = power;
		req.intValue3 = mode;
		sendRequest(req, false);
		return ExceptionWrapper.getCompletedException(null);
	}

	@Override
	public void setPWMMode(int mode) {
		// Not implemented
	}

	@Override
	public Future<ReturnWrapper<Integer>> getTachoCount() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.GET_TACHO_COUNT;
		return AsyncExecutor.execute(() -> sendRequest(req, true).reply);
	}

	@Override
	public Future<ExceptionWrapper> resetTachoCount() {
		EV3Request req = new EV3Request();
		req.request = EV3Request.Request.RESET_TACHO_COUNT;
		sendRequest(req, false);
		return ExceptionWrapper.getCompletedException(null);
	}

	@Override
	public MotorRegulator getRegulator() {
		throw(new UnsupportedOperationException("Remote regulators are not supported"));
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
}
