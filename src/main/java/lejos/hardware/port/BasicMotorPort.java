package lejos.hardware.port;

import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

/**
 * An abstraction for a motor port that supports RCX
 * type motors, but not NXT motors with tachometers.
 * 
 * @author Lawrie Griffiths.
 * 
 */
public interface BasicMotorPort extends IOPort {
    /** PWM Mode. Motor is not driven during off phase of PWM */
    int PWM_FLOAT = 0;
    /** PWM Mode. Motor is driven during off phase of PWM */
    int PWM_BRAKE = 1;
    /** Motor is running forward */
    int FORWARD = 1;
    /** Motor is running backwards */
    int BACKWARD = 2;
    /** Motor is stopped (PWM drive still applied) */
    int STOP = 3;
    /** Motor is floating (no PWM drive) */
    int FLOAT = 4;
    /** Maximum power setting = 100% */
    int MAX_POWER = 100;
	
	Future<ExceptionWrapper> controlMotor(int power, int mode);
	
	void setPWMMode(int mode);
}
