package lejos.robotics;

/**
 * Interface for a regular DC motor.
 * @author BB
 *
 */
public interface DCMotor extends BaseMotor{

    /**
     * Set the power level 0%-100% to be applied to the motor
     * @param power new motor power 0-100
     */
    void setPower(int power) throws Exception;

    /**
     * Returns the current motor power setting.
     * @return current power 0-100
     */
    int getPower() throws Exception;
}
