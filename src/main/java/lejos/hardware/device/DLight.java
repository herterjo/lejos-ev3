package lejos.hardware.device;

import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

/**
 * Interface for dLights from Dexter Industries
 * @author Aswin Bouwmeester
 *
 */
public interface DLight {
	
	/**
	 * Enables the dLight. 
	 */
    Future<ExceptionWrapper> enable();
	
	/**
	 * Disables the dLight.
	 * Values for color and blinking pattern are not overwritten and will 
	 * still be in effect after enabling the dLight again.
	 */
    Future<ExceptionWrapper> disable();
	
	/** Queries the status of the dLight
	 * @return
	 * True, the dLight is enabled. False, the dLight is off.
	 */
    boolean isEnabled();
	
	/**
	 * Changes the color of the LED according to specified RGB color.
	 * Each of the color values should be between 0 (fully off) and 255 (fully on).
	 * @param red
	 * @param green
	 * @param blue
	 */
    Future<ExceptionWrapper> setColor(int red, int green, int blue);
	
	/**
	 * Changes the color of the LED according to specified RGB color.
	 * Each of the color values should be between 0 (fully off) and 255 (fully on).
	 * @param rgb
	 * Integer array containing RGB colors
	 */
    Future<ExceptionWrapper> setColor(int[] rgb);
	
	/**
	 * Changes the color of the LED according to specified HSL color.
	 * @param hue
	 * The hue value in the range of 0-360
	 * @param saturation
	 * The saturation value in the range of 0-100
	 * @param luminosity
	 * The saturation luminosity value in the range of 0-100
	 */
    Future<ExceptionWrapper> setHSLColor(int hue, int saturation, int luminosity);
	
	/**
	 * Specifies the blinking pattern of the LED. Blinnking mode should be enabled 
	 * for the pattern to be in effect.
	 * @param seconds
	 * The total time of a blinking cycle (in seconds)
	 * @param percentageOn
	 * The percentage of the time the LED is on within a blinking cycle
	 */
    Future<ExceptionWrapper> setBlinkingPattern(float seconds, int percentageOn);
	
	/**
	 * Enables blinking pattern. The blinking pattern should be set with the SetBlinkingPattern method.
	 */
    Future<ExceptionWrapper> enableBlinking();
	
	/**
	 * Disables blinking. The blinking pattern itself remains in memory. 
	 */
    Future<ExceptionWrapper> disableBlinking();
	
	/**
	 * Queries the blinking mode of the dLight
	 * @return
	 * True is blinking is enabled. False if blinking is disabled.
	 */
    boolean isBlinkingEnabled();
		
	/**
	 * Returns the RGB color of the LED
	 * Each of the color values is between 0 (fully off) and 255 (fully on).
		 * @param rgb
	 */
    void getColor(int[] rgb);
	
	/**
	 * Sets the PWM value of the external LED driver of the dLight.
	 * Each dLight has two leads broken out that can be used to drive an external LED.
	 * @param value
	 * The values should be between 0 (fully off) and 255 (fully on).
	 */
    Future<ExceptionWrapper> setExternalLED(int value);
	
	/**
	 * Gets the PWM value of the external LED driver of the dLight.
	 * Each dLight has two leads broken out that can be used to drive an external LED.
	 * @return 
	 * The return value is between 0 (fully off) and 255 (fully on).
	 */
    int getExternalLED();
	

}
