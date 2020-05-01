package lejos.robotics;

import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

class ReversedEncoderMotor implements EncoderMotor {

	EncoderMotor encoderMotor;
	
	ReversedEncoderMotor(EncoderMotor motor) {
		this.encoderMotor = motor;
	}
	
	public int getPower() throws Exception {
		return encoderMotor.getPower();
	}

	public void setPower(int power) throws Exception {
		encoderMotor.setPower(power);
	}

	public void backward() throws Exception {
		encoderMotor.forward();
	}

	public void flt() throws Exception {
		encoderMotor.flt();
	}

	public void forward() throws Exception {
		encoderMotor.backward();
	}

	public Future<ReturnWrapper<Boolean>> isMoving() throws Exception {
		return encoderMotor.isMoving();
	}

	public void stop() throws Exception {
		encoderMotor.stop();
	}

	public Future<ReturnWrapper<Integer>> getTachoCount() {
		return AsyncExecutor.execute(() -> -encoderMotor.getTachoCount().get().getValue());
	}

	public Future<ExceptionWrapper> resetTachoCount() throws Exception {
		return encoderMotor.resetTachoCount();
	}
}
