package lejos.robotics;

import java.io.IOException;

/**
 * Interface for real time clock devices
 * 
 * @author Lawrie Griffiths
 *
 */
public interface Clock {
	int getYear() throws IOException;
	
	int getMonth() throws IOException;
	
	int getDay()throws IOException;
	
	int getHour() throws IOException;
	
	int getMinute() throws IOException;
	
	int getSecond() throws IOException;
	
	int getDayOfWeek() throws IOException;
	
	void setHourMode(boolean mode) throws IOException;
	
	String getDateString() throws IOException;
	
	String getTimeString() throws IOException;
	
	String getAMPM() throws IOException;
	
	byte getByte(int loc) throws IndexOutOfBoundsException,IOException;
	
	void setByte(int loc, byte b) throws IndexOutOfBoundsException,IOException;
	
	void setDate(int m, int d, int y) throws IllegalArgumentException,IOException ;
	
	void setTime(int h, int m, int s) throws IllegalArgumentException,IOException;
}
