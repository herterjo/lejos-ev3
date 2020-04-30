package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.Image;

public interface RMIGraphicsLCD extends Remote {
	
    void setPixel(int x, int y, int color) throws RemoteException;
    
    int getPixel(int x, int y) throws RemoteException;
    
    void drawString(String str, int x, int y, int anchor, boolean inverted) throws RemoteException;
    
    void drawString(String str, int x, int y, int anchor) throws RemoteException;
    
    void drawSubstring(String str, int offset, int len,
                       int x, int y, int anchor) throws RemoteException;

    void drawChar(char character, int x, int y, int anchor) throws RemoteException;

    void drawChars(char[] data, int offset, int length,
                   int x, int y, int anchor) throws RemoteException;

    int getStrokeStyle() throws RemoteException;

    void setStrokeStyle(int style) throws RemoteException;

    void drawRegionRop(Image src, int sx, int sy, int w, int h, int x, int y, int anchor, int rop) throws RemoteException;
    
    void drawRegionRop(Image src, int sx, int sy, int w, int h, int transform, int x, int y, int anchor, int rop) throws RemoteException;

    void drawRegion(Image src,
                    int sx, int sy,
                    int w, int h,
                    int transform,
                    int x, int y,
                    int anchor) throws RemoteException;

    void drawImage(Image src, int x, int y, int anchor) throws RemoteException;

    void drawLine(int x0, int y0, int x1, int y1) throws RemoteException;

    void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) throws RemoteException;

    void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) throws RemoteException;

    void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) throws RemoteException;
 
    void drawRect(int x, int y, int width, int height) throws RemoteException;

    void fillRect(int x, int y, int w, int h) throws RemoteException;

    void copyArea(int sx, int sy,
                  int w, int h,
                  int x, int y, int anchor) throws RemoteException;

    Font getFont() throws RemoteException;

    void setFont(Font f) throws RemoteException;

    void translate(int x, int y) throws RemoteException;

    int getTranslateX() throws RemoteException;

    int getTranslateY() throws RemoteException;

    void setColor(int rgb) throws RemoteException;

    void setColor(int red, int green, int blue) throws RemoteException;
    
    void refresh() throws RemoteException;
    
    void clear() throws RemoteException;
    
    int getWidth() throws RemoteException;
    
    int getHeight() throws RemoteException;
    
    byte[] getDisplay() throws RemoteException;
    
    byte[] getHWDisplay() throws RemoteException;
    
    void setContrast(int contrast) throws RemoteException;
    
    void bitBlt(byte[] src, int sw, int sh, int sx, int sy, int dx, int dy, int w, int h, int rop) throws RemoteException;
    
    void bitBlt(byte[] src, int sw, int sh, int sx, int sy, byte[] dst, int dw, int dh, int dx, int dy, int w, int h, int rop) throws RemoteException;
    
    void setAutoRefresh(boolean on) throws RemoteException;
    
    int setAutoRefreshPeriod(int period) throws RemoteException;

}
