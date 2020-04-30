package lejos.remote.ev3;

public interface Menu {
	
	void runProgram(String programName);
	
	void runSample(String programName);
	
	void debugProgram(String programName);
	
	boolean deleteFile(String fileName);
	
	void stopProgram();
	
	long getFileSize(String filename);
	
	String[] getProgramNames();
	
	String[] getSampleNames();

	boolean uploadFile(String fileName, byte[] contents);
	
	byte[] fetchFile(String fileName);
	
	String getSetting(String setting);
	
	void setSetting(String setting, String value);
	
	void deleteAllPrograms();
	
	String getVersion();
	
	String getMenuVersion();
	
	String getName();
	
	void setName(String name);
	
	String getExecutingProgramName();
	
	void shutdown();
	
	void suspend();
	
	void resume();
}
