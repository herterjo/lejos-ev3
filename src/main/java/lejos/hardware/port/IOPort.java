package lejos.hardware.port;

import lejos.utility.ExceptionWrapper;

import java.io.Closeable;
import java.util.concurrent.Future;

/**
 * Basic interface for EV3 sensor ports.
 * TODO: Need to cleanup the setting of modes etc. should probably become
 * part of the open, with some sort of name/value string pairs.
 * @author andy
 *
 */
public interface IOPort extends Closeable   {
   
    /**
     * Close the port async, the port can not be used after this call.
     * @return
     */
    public void close();

    /**
     * Close the port async, the port can not be used after this call.
     * @return
     */
    public Future<ExceptionWrapper> closeRet();
    
    /**
     * Return the string representing this port
     * @return the port name
     */
    public String getName();
    
   /**
     * Set the port pins up ready for use.
     * @param mode The EV3 pin mode
     * @return true if the operation succeeds false if it fails
     */
    public boolean setPinMode(int mode);
    
}