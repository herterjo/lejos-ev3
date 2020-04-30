package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIMenu extends Remote {
	
	void runProgram(String programName) throws RemoteException;
	
	void debugProgram(String programName) throws RemoteException;
	
	void runSample(String programName) throws RemoteException;
	
	void stopProgram() throws RemoteException;
	
	boolean deleteFile(String fileName) throws RemoteException;
	
	long getFileSize(String fileName) throws RemoteException;
	
	String[] getProgramNames() throws RemoteException;
	
	String[] getSampleNames()  throws RemoteException;
	
	boolean uploadFile(String fileName, byte[] contents) throws RemoteException;
	
	byte[] fetchFile(String fileName) throws RemoteException;
	
	String getSetting(String setting) throws RemoteException;
	
	void setSetting(String setting, String value) throws RemoteException;
	
	void deleteAllPrograms() throws RemoteException;
	
	String getVersion() throws RemoteException;
	
	String getMenuVersion() throws RemoteException;
	
	String getName() throws RemoteException;
	
	void setName(String name) throws RemoteException;
	
	void configureWifi(String ssid, String pwd) throws RemoteException;
	
	String getExecutingProgramName() throws RemoteException;
	
	void shutdown() throws RemoteException;
	
	void suspend() throws RemoteException;
	
	void resume() throws RemoteException;

}
