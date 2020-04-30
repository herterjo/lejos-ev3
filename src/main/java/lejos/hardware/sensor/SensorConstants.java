package lejos.hardware.sensor;

/**
 * Constants used to set Sensor types and modes.
 *
 */
public interface SensorConstants {

	/**
     * Colors used as the output value when in full mode. Values are
     * compatible with LEGO firmware. Note that these color values
     * are normally converted to use the standard leJOS colors
     * as defined in the Color class.
     */

    int BLACK = 1;
    int BLUE = 2;
    int GREEN = 3;
    int YELLOW = 4;
    int RED = 5;
    int WHITE = 6;
    int BROWN = 7;
    /** Color sensor data RED value index. */
    int RED_INDEX = 0;
    /** Color sensor data GREEN value index. */
    int GREEN_INDEX = 1;
    /** Color sensor data BLUE value index. */
    int BLUE_INDEX = 2;
    /** Color sensor data BLANK/Background value index. */
    int BLANK_INDEX = 3;

	int TYPE_NO_SENSOR = 0;
	int TYPE_SWITCH = 1;
	int TYPE_TEMPERATURE = 2;
	int TYPE_REFLECTION = 3;
	int TYPE_ANGLE = 4;
	int TYPE_LIGHT_ACTIVE = 5;
	int TYPE_LIGHT_INACTIVE = 6;
	int TYPE_SOUND_DB = 7;
	int TYPE_SOUND_DBA = 8;
	int TYPE_CUSTOM = 9;
	int TYPE_LOWSPEED = 10;
	int TYPE_LOWSPEED_9V = 11;
    int TYPE_HISPEED = 12;
    int TYPE_COLORFULL = 13;
    int TYPE_COLORRED = 14;
    int TYPE_COLORGREEN = 15;
    int TYPE_COLORBLUE = 16;
    int TYPE_COLORNONE = 17;
    // additional leJOS types for the EV3
    int TYPE_HIGHSPEED = 18;
    int TYPE_HIGHSPEED_9V = 19;
    
    int MIN_TYPE = 0;
    int MAX_TYPE = 19;

    // Only RAW mode on the EV3
    int MODE_RAW = 0x00;
    /** MAX value returned as a RAW sensor reading for standard NXT A/D sensors */
    int NXT_ADC_RES = 1023;
}
