package lejos.internal.io;

public class NativeDeviceWriteCommand {
    private byte[] toWrite;
    private int len;
    private String deviceName;
    private int offset;

    public NativeDeviceWriteCommand(byte[] toWrite, int toWriteLen, String deviceName, int offset) {
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
