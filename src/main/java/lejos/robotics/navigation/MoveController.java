package lejos.robotics.navigation;

import java.util.concurrent.ExecutionException;

public interface MoveController extends MoveProvider {
	/**
	 * NXT 1.0 kit wheel diameter, in centimeters
	 */
    double WHEEL_SIZE_NXT1 = 5.60;

	/**
	 * NXT 2.0 kit wheel diameter, in centimeters
	 */
    double WHEEL_SIZE_NXT2 = 4.32;

	/**
	 * EV3 kit wheel diameter, in centimeters
	 */
    double WHEEL_SIZE_EV3 = WHEEL_SIZE_NXT2;
	
	/**
	 * White RCX "motorcycle" wheel diameter, in centimeters
	 */
    double WHEEL_SIZE_RCX  = 8.16;

	/**
	 *Starts the  NXT robot moving forward.
	 */
    void forward() throws Exception;

	/**
	 *Starts the  NXT robot moving backwards.
	 */
    void backward() throws Exception;

	/**
	 * Halts the NXT robot
	 */
    void stop() throws Exception;

	/**
	 * true if the robot is moving 
	 * @return true if the robot is moving under power.
	 */
    boolean isMoving() throws Exception;
	

	/**
	 * Moves the NXT robot a specific distance. A positive value moves it forward and a negative value moves it backward.
	 * Method returns when movement is done.
	 * 
	 * @param distance The positive or negative distance to move the robot.
	 */
    void travel(double distance) throws Exception;

	/**
	 * Moves the NXT robot a specific distance. A positive value moves it forward and a negative value moves it backward.
	 * @param distance The positive or negative distance to move the robot, in wheel diameter units.
	 * @param immediateReturn If immediateReturn is true then the method returns immediately.
	 */
    void travel(double distance, boolean immediateReturn) throws Exception;

	/**
	 * Sets the speed at which the robot will travel forward and backward (and to some extent arcs, although actual arc speed
	 * is slightly less). Speed is measured in units/second. e.g. If wheel diameter is cm, then speed is cm/sec.
	 * @param speed In chosen units per second (e.g. cm/sec)
	 */
    void setLinearSpeed(double speed) throws Exception;

	/**
	 * Returns the speed at which the robot will travel forward and backward (and to some extent arcs, although actual arc speed
	 * is slightly less). Speed is measured in units/second. e.g. If wheel diameter is cm, then speed is cm/sec.
	 * @return Speed in chosen units per second (e.g. cm/sec)
	 */
    double getLinearSpeed();

	/**
	 * Returns the maximum speed at which this robot is capable of traveling forward and backward.
	 * Speed is measured in units/second. e.g. If wheel diameter is cm, then speed is cm/sec.
	 * @return Speed in chosen units per second (e.g. cm/sec)
	 */
    double getMaxLinearSpeed() throws Exception;
	
	/**
   * Sets the acceleration at which the robot will accelerate at the start of a move and decelerate at the end of a move.
   * Acceleration is measured in units/second^2. e.g. If wheel diameter is cm, then acceleration is cm/sec^2. <p>
   * If acceleration is set during a move it will not be in used for the current move, it will be in effect with the next move.
	 * @param acceleration in chosen units/second^2
	 */
    void setLinearAcceleration(double acceleration);
	
	/** Returns the acceleration at which the robot accelerates at the start of a move and decelerates at the end of a move.
	 * @return acceleration in chosen units/second^2
	 */
    double getLinearAcceleration();

}
