package lejos.internal.io;

import java.util.LinkedList;
import java.util.List;

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
                if (toWrite != null) {
                    writeToFile(toWrite);
                } else {
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    public void write(byte[] toWrite, int len, String name) {
        write(toWrite, len, name, 0);
    }

    public void write(byte[] toWrite, int len, String name, int offset) {
        NativeDeviceWriteCommand cmd = new NativeDeviceWriteCommand(toWrite, len, name, offset);
        write(cmd);
    }

    public void write(NativeDeviceWriteCommand write) {
        synchronized (writes) {
            writes.add(write);
            notify();
        }
    }
}
