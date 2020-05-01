package lejos.hardware.port;

import lejos.hardware.sensor.SensorConstants;

/**
 * An abstraction for a sensor port that supports 
 * setting and retrieving types and modes of sensors.
 * 
 * @author Lawrie Griffiths.
 *
 */
public interface BasicSensorPort extends SensorConstants {

    /**
     * Get the current operating mode of the sensor
     * @return the current mode
     */
    int getMode();
	
    /**
     * Get the current type setting. Note that types are typically used to 
     * control the operation of Legacy and i2c sensors. They are normally not
     * used for EV3 sensors. 
     * @return the current sensor type
     */
    int getType();

    /**
     * Set the current operating mode for the sensor attached to the port.
     * @param mode the new mode
     * @return true if the mode has been accepted
     */
    boolean setMode(int mode);

    /**
     * Set the operating type for the attached sensor. Normally type setting is
     * only used with legacy sensors and for i2c devices (to set the speed and
     * operating voltage). It is not normally used with EV3 sensors.
     * @param type
     * @return true if type accepted
     */
    boolean setType(int type);
	
    @Deprecated
    boolean setTypeAndMode(int type, int mode);

}

