package lejos.robotics;

import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/**
 * Abstraction for the tachometer built into NXT motors.
 * 
 * @author Lawrie Griffiths
 *
 */
public interface Encoder {
	
	  /**
	   * Returns the tachometer count.
	   * 
	   * @return tachometer count in degrees
	   */
	  public Future<ReturnWrapper<Integer>> getTachoCount();

	  
	  /**
	   * Reset the tachometer count.
       * @return
       */
	  public Future<ExceptionWrapper> resetTachoCount();

}
