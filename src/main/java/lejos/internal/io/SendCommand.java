package lejos.internal.io;

import lejos.hardware.port.I2CWritePort;

/**
 * Class to parameterize the NativeDeviceasyncWriter.write function
 */
public class SendCommand {
    private final byte[] toWrite;
    private final int len;
    private final int offset;
    private final SendCommandType type;
    private final int address;
    private final I2CWritePort port;
    private final NativeDevice device;

    /**
     * Creates a new instance for writing
     *
     * @param toWrite    Array to write
     * @param toWriteLen How many bytes to write from the array, can't be greater than toWrite.length
     * @param offset     Specifies when to write, can't be less than 0
     */
    public SendCommand(byte[] toWrite, int toWriteLen, int offset, NativeDevice device) {
        this(toWrite, toWriteLen, offset, -1, null, device, SendCommandType.write);
    }

    /**
     * Creates a new instance for ioctl
     *
     * @param toWrite    Array to write
     * @param toWriteLen How many bytes to write from the array, can't be greater than toWrite.length
     * @param address    Port to write to
     * @param offset     Specifies when to write, can't be less than 0
     */
    public SendCommand(byte[] toWrite, int toWriteLen, int offset, int address, I2CWritePort port) {
        this(toWrite, toWriteLen, offset, address, port, null, SendCommandType.ioctl);
    }

    private SendCommand(byte[] toWrite, int toWriteLen, int offset, int address, I2CWritePort port,
                        NativeDevice device, SendCommandType type) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset less than 0 is not supported");
        }
        if (toWriteLen < toWrite.length) {
            throw new IllegalArgumentException("toWriteLen was specified greater then actual array length");
        }
        this.toWrite = toWrite;
        this.len = toWriteLen;
        this.offset = offset;
        this.type = type;
        this.address = address;
        this.port = port;
        this.device = device;
    }

    public byte[] getToWrite() {
        return toWrite;
    }

    public int getLen() {
        return len;
    }

    public int getOffset() {
        return offset;
    }

    public int getAddress() {
        return address;
    }

    public SendCommandType getType() {
        return type;
    }

    public I2CWritePort getPort() {
        return port;
    }

    public NativeDevice getDevice() {
        return device;
    }
}
