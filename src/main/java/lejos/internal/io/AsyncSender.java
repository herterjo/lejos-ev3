package lejos.internal.io;

import lejos.hardware.port.I2CWritePort;

import java.util.LinkedList;
import java.util.List;

/**
 * Helper to write tasks asyncronously (for example a command for a motor), no exceptions, no return values
 */
public class AsyncSender {
    private final List<SendCommand> writes = new LinkedList<>();
    private static AsyncSender instance;

    private AsyncSender() {
        new Thread(() ->
        {
            while (true) {
                SendCommand toWrite;
                synchronized (writes) {
                    if (writes.size() > 0) {
                        toWrite = writes.remove(0);
                    } else {
                        toWrite = null;
                    }
                }
                try {
                    if (toWrite != null) {
                        writeToFile(toWrite);
                    } else {
                        wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static AsyncSender getInstance() {
        if (instance == null) {
            instance = new AsyncSender();
        }
        return instance;
    }

    private void writeToFile(SendCommand toWrite) {
        if (toWrite.getType().equals(SendCommandType.write)) {
            toWrite.getDevice().write(toWrite.getToWrite(), toWrite.getOffset(), toWrite.getLen());
        } else if (toWrite.getType().equals(SendCommandType.ioctl)) {
            toWrite.getPort().i2cWrite(toWrite.getAddress(), toWrite.getToWrite(), toWrite.getOffset(), toWrite.getLen());
        }
    }

    /**
     * Writes the specified array with offset 0
     *
     * @param toWrite Array to write
     * @param len     How many bytes to write from the array
     * @param port    Adress of port to write to
     */
    public void ioctl(byte[] toWrite, int len, int address, I2CWritePort port) {
        ioctl(toWrite, len, address, port, 0);
    }

    /**
     * Writes the specified array
     *
     * @param toWrite Array to write
     * @param len     How many bytes to write from the array
     * @param address Adress of port to write to
     * @param offset  Specifies when to write
     * @param port    Port to write to
     */
    public void ioctl(byte[] toWrite, int len, int address, I2CWritePort port, int offset) {
        SendCommand cmd = new SendCommand(toWrite, len, offset, address, port);
        addCommand(cmd);
    }

    /**
     * Writes the specified array with offset 0
     *
     * @param toWrite Array to write
     * @param len     How many bytes to write from the array
     * @param name    Name of the file to write to
     */
    public void write(byte[] toWrite, int len, String name) {
        write(toWrite, len, new NativeDevice(name), 0);
    }

    /**
     * Writes the specified array
     *
     * @param toWrite Array to write
     * @param len     How many bytes to write from the array
     * @param offset  Specifies when to write
     */
    public void write(byte[] toWrite, int len, NativeDevice device, int offset) {
        SendCommand cmd = new SendCommand(toWrite, len, offset, device);
        addCommand(cmd);
    }

    /**
     * Writes the specified array
     *
     * @param write Parameter object for the write operation
     */
    public void addCommand(SendCommand write) {
        synchronized (writes) {
            writes.add(write);
            notify();
        }
    }
}
