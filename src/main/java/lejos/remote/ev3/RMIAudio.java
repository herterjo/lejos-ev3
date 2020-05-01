package lejos.remote.ev3;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIAudio extends Remote
{        
    /**
     * Play a system sound.
     * <TABLE BORDER=1>
     * <TR><TH>aCode</TH><TH>Resulting Sound</TH></TR>
     * <TR><TD>0</TD><TD>short beep</TD></TR>
     * <TR><TD>1</TD><TD>double beep</TD></TR>
     * <TR><TD>2</TD><TD>descending arpeggio</TD></TR>
     * <TR><TD>3</TD><TD>ascending  arpeggio</TD></TR>
     * <TR><TD>4</TD><TD>long, low buzz</TD></TR>
     * </TABLE>
     */
    void systemSound(int aCode) throws RemoteException;
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param aFrequency The frequency of the tone in Hertz (Hz).
     * @param aDuration The duration of the tone, in milliseconds.
     * @param aVolume The volume of the playback 100 corresponds to 100%
     */
    void playTone(int aFrequency, int aDuration, int aVolume) throws RemoteException;
    

    void playTone(int freq, int duration) throws RemoteException;

    
    /**
     * Play a wav file
     * @param file the 8-bit PWM (WAV) sample file
     * @param vol the volume percentage 0 - 100
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    int playSample(File file, int vol) throws RemoteException;


    /**
     * Play a wav file
     * @param file the 8-bit PWM (WAV) sample file
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    int playSample(File file)  throws RemoteException;

    /**
     * Queue a series of PCM samples to play at the
     * specified volume and sample rate.
     * 
     * @param data Buffer containing the samples
     * @param offset Offset of the first sample in the buffer
     * @param len Number of samples to queue
     * @param freq Sample rate
     * @param vol playback volume
     * @return Number of samples actually queued
     */
    int playSample(byte[] data, int offset, int len, int freq, int vol) throws RemoteException;

    /**
     * Play a note with attack, decay, sustain and release shape. This function
     * allows the playing of a more musical sounding note. It uses a set of
     * supplied "instrument" parameters to define the shape of the notes 
     * envelope.
     * @param inst Instrument definition
     * @param freq The note to play (in Hz)
     * @param len  The duration of the note (in ms)
     */
    void playNote(int[] inst, int freq, int len)  throws RemoteException;
    
    /**
     * Set the master volume level
     * @param vol 0-100
     */
    void setVolume(int vol)  throws RemoteException;

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    int getVolume()  throws RemoteException;
    
    /**
     * Load the current system settings associated with this class. Called
     * automatically to initialize the class. May be called if it is required
     * to reload any settings.
     */
    void loadSettings()  throws RemoteException;
}
