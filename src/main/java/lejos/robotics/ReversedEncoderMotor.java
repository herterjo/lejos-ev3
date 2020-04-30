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
	
	public int getPower() {
		return encoderMotor.getPower();
	}

	public void setPower(int power) {
		encoderMotor.setPower(power);
	}

	public void backward() {
		encoderMotor.forward();
	}

	public void flt() {
		encoderMotor.flt();
	}

	public void forward() {
		encoderMotor.backward();
	}

	public Future<ReturnWrapper<Boolean>> isMoving() {
		return encoderMotor.isMoving();
	}

	public void stop() {
		encoderMotor.stop();
	}

	public Future<ReturnWrapper<Integer>> getTachoCount() {
		return AsyncExecutor.execute(() -> -encoderMotor.getTachoCount().get().getValue());
	}

	public Future<ExceptionWrapper> resetTachoCount() {
		return encoderMotor.resetTachoCount();
	}
}
