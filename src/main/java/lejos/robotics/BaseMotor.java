package lejos.robotics;

import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/**
 * Base motor interface. Contains basic movement commands.
 *
 */
public interface BaseMotor {

	/**
	   * Causes motor to rotate forward until <code>stop()</code> or <code>flt()</code> is called.
	   */
	  void forward() throws Exception;

	  /**
	   * Causes motor to rotate backwards until <code>stop()</code> or <code>flt()</code> is called.
	   */
	  void backward() throws Exception;

	  /**
	   * Causes motor to stop immediately. It will resist any further motion. Cancels any rotate() orders in progress.
	   */
	  void stop() throws Exception;

	  /**
	   * Motor loses all power, causing the rotor to float freely to a stop.
	   * This is not the same as stopping, which locks the rotor.
	   */
	  public void flt() throws Exception;

	  /**
	   * Return <code>true</code> if the motor is moving.
	   *
	   * @return <code>true</code> if the motor is currently in motion, <code>false</code> if stopped.
	   */
	  // TODO: Possibly part of Encoder interface? Depends if encoder used to determine this.
	  Future<ReturnWrapper<Boolean>> isMoving() throws Exception;

}
