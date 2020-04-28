package lejos.internal.io;

/**
 * Class to parameterize the NativeDeviceasyncWriter.write function
 */
public class NativeDeviceWriteCommand {
    private final byte[] toWrite;
    private final int len;
    private final String deviceName;
    private final int offset;

    /**
     * Creates a new instance
     * @param toWrite Array to write
     * @param toWriteLen How many bytes to write from the array, can't be greater than toWrite.length
     * @param deviceName Name of the file to write to
     * @param offset Specifies when to write, can't be less than 0
     */
    public NativeDeviceWriteCommand(byte[] toWrite, int toWriteLen, String deviceName, int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset less than 0 is not supported");
        }
        if (toWriteLen < toWrite.length) {
            throw new IllegalArgumentException("toWriteLen was specified greater then actual array length");
        }
        this.toWrite = toWrite;
        this.len = toWriteLen;
        this.deviceName = deviceName;
        this.offset = offset;
    }

    public byte[] getToWrite() {
        return toWrite;
    }

    public int getLen() {
        return len;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getOffset() {
        return offset;
    }
}
