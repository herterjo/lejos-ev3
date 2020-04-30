package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

import lejos.hardware.lcd.Font;

public interface RMIEV3 extends Remote {

	RMIAnalogPort openAnalogPort(String portName) throws Exception;
	
	RMII2CPort openI2CPort(String portName) throws Exception;

	RMIBattery getBattery() throws RemoteException;

	RMIUARTPort openUARTPort(String portName) throws Exception;

	RMIMotorPort openMotorPort(String portName) throws Exception;
	
	RMISampleProvider createSampleProvider(String portName, String sensorName, String modeName) throws RemoteException;
	
	RMIRegulatedMotor createRegulatedMotor(String portName, char motorType) throws Exception;

	RMIAudio getAudio() throws RemoteException;
	
	RMITextLCD getTextLCD() throws RemoteException;
	
	RMITextLCD getTextLCD(Font f) throws RemoteException;
	
	RMIGraphicsLCD getGraphicsLCD() throws RemoteException;
	
	RMIWifi getWifi() throws RemoteException;
	
	RMIBluetooth getBluetooth() throws RemoteException;
	
	String getName() throws RemoteException;
	
	RMIKey getKey(String name) throws RemoteException;

	RMILED getLED() throws RemoteException;

	RMIKeys getKeys()  throws RemoteException;
	
}
