package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

import lejos.hardware.lcd.Font;

public interface RMITextLCD extends Remote {

	void refresh() throws RemoteException;

	void clear()  throws RemoteException;

	int getWidth()  throws RemoteException;

	int getHeight()  throws RemoteException;

	byte[] getDisplay()  throws RemoteException;

	byte[] getHWDisplay()  throws RemoteException;

	void setContrast(int contrast)  throws RemoteException;

	void bitBlt(byte[] src, int sw, int sh, int sx, int sy, int dx,
                int dy, int w, int h, int rop)  throws RemoteException;

	void bitBlt(byte[] src, int sw, int sh, int sx, int sy, byte[] dst,
                int dw, int dh, int dx, int dy, int w, int h, int rop)  throws RemoteException;

	void setAutoRefresh(boolean on)  throws RemoteException;

	int setAutoRefreshPeriod(int period)  throws RemoteException;

	void drawChar(char c, int x, int y)  throws RemoteException;

	void drawString(String str, int x, int y, boolean inverted)  throws RemoteException;

	void drawString(String str, int x, int y)  throws RemoteException;

	void drawInt(int i, int x, int y)  throws RemoteException;

	void drawInt(int i, int places, int x, int y)  throws RemoteException;

	void clear(int x, int y, int n) throws RemoteException;

	void clear(int y)  throws RemoteException;

	void scroll()  throws RemoteException;

	Font getFont()  throws RemoteException;

	int getTextWidth()  throws RemoteException;

	int getTextHeight()  throws RemoteException;

}
