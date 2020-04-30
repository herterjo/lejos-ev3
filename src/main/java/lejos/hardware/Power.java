package lejos.hardware;

/**
 * Interface used to access information about the EV3 battery and current usage.
 * @author Brian Bagnall, Lawrie Griffiths Andy Shaw
 *
 */
public interface Power {
		
	/**
	 * The NXT uses 6 batteries of 1500 mV each.
	 * @return Battery voltage in mV. ~9000 = full.
	 */
    int getVoltageMilliVolt();

	/**
	 * The NXT uses 6 batteries of 1.5 V each.
	 * @return Battery voltage in Volt. ~9V = full.
	 */
    float getVoltage();

	/**
	 * Return the current draw from the battery
	 * @return current in Amps
	 */
    float getBatteryCurrent();

	/**
	 * return the motor current draw
	 * @return current in Amps
	 */
    float getMotorCurrent();
}

