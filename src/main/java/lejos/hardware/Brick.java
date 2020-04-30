package lejos.hardware;

import lejos.hardware.Audio;
import lejos.hardware.Power;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.video.Video;

public interface Brick
{
    /**
     * Return a port object for the request port name. This allows access to the
     * hardware associated with the specified port.
     * @param portName The name of port
     * @return the request port
     */
    Port getPort(String portName);
    
    /**
     * return a battery object which can be used to obtain battery voltage etc.
     * @return A battery object
     */
    Power getPower();

    /**
     * return a Audio object which can be used to access the device's audio playback
     * @return A Audio device
     */
    Audio getAudio();

    Video getVideo();
    /**
     * Get text access to the LCD using the default font
     * @return the text LCD 
     */
    TextLCD getTextLCD();
    
    /**
     * Get text access to the LCD using a specified font
     * @param f the font
     * @return the text LCD
     */
    TextLCD getTextLCD(Font f);
    
    /**
     * Get graphics access to the LCD
     * @return the graphics LCD
     */
    GraphicsLCD getGraphicsLCD();
    
    /**
     * Test whether the brick is a local one
     * @return true iff brick is local
     */
    boolean isLocal();
    
    /**
     * Get the type of brick, e.g. "EV3", "NXT", "BrickPi"
     * @return the brick type
     */
    String getType();
    
    /**
     * Get he name of the brick
     * @return the name
     */
    String getName();
    
    /**
     * Get the local Bluetooth device
     * @return the local Bluetooth device
     */
    LocalBTDevice getBluetoothDevice();
    
    /**
     * Get the local Wifi device
     * @return the local Wifi device
     */
    LocalWifiDevice getWifiDevice();
    
    /**
     * Set this brick as the default one for static methods
     */
    void setDefault();
    
    /**
     * Get access to the keys (buttons)
     * @return an implementation of the Keys interface
     */
    Keys getKeys();
    
    /**
     * Get access to a specific Key (aka Button)
     * @param name the key name
     * @return an implementation of the Key interface
     */
    Key getKey(String name);
    
    /**
     * Get access to the LED
     * @return an implementation of the LED interface
     */
    LED getLED();
    
}
