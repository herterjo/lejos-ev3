package lejos.robotics;

import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/**
 * Abstraction for a Tachometer, which monitors speed of the encoder.
 *
 * @author BB
 *
 */
public interface Tachometer extends Encoder {
	
	
	  /**
	   * Returns the actual speed.
	   * 
	   * @return speed in degrees per second, negative value means motor is rotating backward
	   */
	  Future<ReturnWrapper<Integer>> getRotationSpeed() throws Exception;

}
