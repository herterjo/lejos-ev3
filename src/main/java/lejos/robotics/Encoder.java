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
      Future<ReturnWrapper<Integer>> getTachoCount() throws Exception;

	  
	  /**
	   * Reset the tachometer count.
       * @return
       */
      Future<ExceptionWrapper> resetTachoCount() throws Exception;

}
