package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

import lejos.hardware.port.BasicMotorPort;

public interface RMIMotorPort extends Remote {
	
    /**
     * Low-level method to control a motor. 
     * 
     * @param power power from 0-100
     * @param mode defined in <code>BasicMotorPort</code>. 1=forward, 2=backward, 3=stop, 4=float.
     * @see BasicMotorPort#FORWARD
     * @see BasicMotorPort#BACKWARD
     * @see BasicMotorPort#FLOAT
     * @see BasicMotorPort#STOP
     */
    void controlMotor(int power, int mode) throws RemoteException;


    /**
     * returns tachometer count
     */
    int getTachoCount() throws Exception;
    
    /**
     *resets the tachometer count to 0;
     */
    void resetTachoCount() throws Exception;
    
    void setPWMMode(int mode) throws RemoteException;
    
    void close() throws RemoteException;
    
}
