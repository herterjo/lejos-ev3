package lejos.hardware.motor;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/**
 * Interface for motor regulation
 * regulate velocity; also stop motor at desired rotation angle.

 **/
public interface MotorRegulator
{
    int NO_LIMIT = 0x7fffffff;


    /**
     * Set the motion control parameters used by the regulator.
     * @param typ The type of motor
     * @param moveP The Proportional control value used while moving
     * @param moveI The integral control parameter used while moving
     * @param moveD The differential control parameter used while moving
     * @param holdP The Proportional control value used while holding position
     * @param holdI The integral control value used while holding position
     * @param holdD The differential control value used while holding position
     * @param offset Motor PWM offset value range 0-10000.
     * @return
     */
    Future<ExceptionWrapper> setControlParamaters(int typ, float moveP, float moveI, float moveD, float holdP, float holdI, float holdD, int offset);
    /**
     * Get the current hardware tachometer reading for the motor,
     * @return hardware reading
     */
    Future<ReturnWrapper<Integer>> getTachoCount();
    
    /**
     * Reset the tachometer base value, after this call the tachometer will return
     * zero for the current position. Note that any in progress movements will be
     * aborted.
     * @return
     */
    Future<ExceptionWrapper> resetTachoCount();

    /**
     * Return true if the motor is currently active
     * @return True if the motor is moving.
     */
    Future<ReturnWrapper<Boolean>> isMoving();
    
    /**
     * Return the current velocity (in degrees/second) that the motor is currently
     * running at. Note that this value may be supplied from the internal
     * control model not from actually measuring the rotation speed. If the regulator
     * is functioning correctly this will closely match the actual velocity
     * @return velocity
     */
    Future<ReturnWrapper<Float>> getCurrentVelocity();
    
    /**
     * Set the stall detection parameters. The motor will be declared as
     * stalled if the error in the motor position exceeds the specified value for
     * longer than the given time.
     * @param error
     * @param time
     */
    void setStallThreshold(int error, int time);

    /**
     * return the regulations models current position. 
     * @return the models current position
     */
    Future<ReturnWrapper<Float>> getPosition();

    /**
     * Initiate a new move and optionally wait for it to complete.
     * If some other move is currently executing then ensure that this move
     * is terminated correctly and then start the new move operation.
     * @param speed
     * @param acceleration
     * @param limit
     * @param hold
     * @param waitComplete
     * @return
     */
    Future<ExceptionWrapper> newMove(float speed, int acceleration, int limit, boolean hold, boolean waitComplete);

    /**
     * The target speed has been changed. Reflect this change in the
     * regulator.
     * @param newSpeed new target speed.
     * @return
     */
    Future<ExceptionWrapper> adjustSpeed(float newSpeed);

    /**
     * The target acceleration has been changed. Updated the regulator.
     * @param newAcc
     * @return
     */
    Future<ExceptionWrapper> adjustAcceleration(int newAcc);
    
    /**
     * Wait until the current movement operation is complete (this can include
     * the motor stalling).
     * @return
     */
    Future<ExceptionWrapper> waitComplete();
    
    /**
     * Add a motor listener. Move operations will be reported to this object.
     * @param motor
     * @param listener
     */
    void addListener(RegulatedMotor motor, RegulatedMotorListener listener);
    
    RegulatedMotorListener removeListener();


    /**
     * Return the angle that this Motor is rotating to.
     * @return angle in degrees
     */
    int getLimitAngle();
    
    /**
     * Return true if the motor is currently stalled.
     * @return true if the motor is stalled, else false
     */
    Future<ReturnWrapper<Boolean>> isStalled();
    
    /**
     * Begin a set of synchronized motor operations
     * @return
     */
    Future<ExceptionWrapper> startSynchronization();
    
    /**
     * Complete a set of synchronized motor operations.
     * @return
     */
    Future<ExceptionWrapper> endSynchronization(boolean b);
    
    /**
     * Specify a set of motors that should be kept in synchronization with this one.
     * The synchronization mechanism simply ensures that operations between a startSynchronization
     * call and an endSynchronization call will all be executed at the same time (when the 
     * endSynchronization method is called). This is all that is needed to ensure that motors
     * will operate in a synchronized fashion. The start/end methods can also be used to ensure
     * that reads of the motor state will also be consistent.
     * @param rl an array of motors to synchronize with.
     */
    void synchronizeWith(MotorRegulator[] rl);


}


