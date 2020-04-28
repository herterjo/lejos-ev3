package lejos.internal.io;

import java.util.LinkedList;
import java.util.List;

/**
 * Helper to write tasks asyncronously (for example a command for a motor), no exceptions, no return values
 */
public class NativeDeviceAsyncWriter {
    private final List<NativeDevice> devices = new LinkedList<>();
    private final List<NativeDeviceWriteCommand> writes = new LinkedList<>();
    private static NativeDeviceAsyncWriter instance;

    private NativeDeviceAsyncWriter() {
        new Thread(() ->
        {
            while (true) {
                NativeDeviceWriteCommand toWrite;
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

    public static NativeDeviceAsyncWriter getInstance() {
        if (instance == null) {
            instance = new NativeDeviceAsyncWriter();
        }
        return instance;
    }

    private void writeToFile(NativeDeviceWriteCommand toWrite) {
        synchronized (devices) {
            NativeDevice device;
            NativeDevice[] matchingDevices = devices
                    .stream().filter(d -> d.getName().equals(toWrite.getDeviceName()))
                    .toArray(NativeDevice[]::new);
            if (matchingDevices.length > 1) {
                throw new IllegalStateException("More than one device matches this name," +
                        " this is a programming errror and should not happen");
            } else if (matchingDevices.length == 1) {
                device = matchingDevices[0];
            } else {
                device = new NativeDevice(toWrite.getDeviceName());
                devices.add(device);
            }
            device.write(toWrite.getToWrite(), toWrite.getOffset(), toWrite.getLen());
        }
    }

    /**
     * Writes the specified array with offset 0
     * @param toWrite Array to write
     * @param len How many bytes to write from the array
     * @param name Name of the file to write to
     */
    public void write(byte[] toWrite, int len, String name) {
        write(toWrite, len, name, 0);
    }

    /**
     * Writes the specified array
     * @param toWrite Array to write
     * @param len How many bytes to write from the array
     * @param name Name of the file to write to
     * @param offset Specifies when to write
     */
    public void write(byte[] toWrite, int len, String name, int offset) {
        NativeDeviceWriteCommand cmd = new NativeDeviceWriteCommand(toWrite, len, name, offset);
        write(cmd);
    }

    /**
     * Writes the specified array
     * @param write Parameter object for the write operation
     */
    public void write(NativeDeviceWriteCommand write) {
        synchronized (writes) {
            writes.add(write);
            notify();
        }
    }
}
