package lejos.internal.ev3;

import lejos.hardware.LED;
import lejos.internal.io.NativeDevice;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;

import java.util.concurrent.Future;

public class EV3LED implements LED {
    private static final NativeDevice device = new NativeDevice("/dev/lms_ui");
    public static int COLOR_NONE = 0;
    public static int COLOR_GREEN = 1;
    public static int COLOR_RED = 2;
    public static int COLOR_ORANGE = 3;
    public static int PATTERN_ON = 0;
    public static int PATTERN_BLINK = 1;
    public static int PATTERN_HEARTBEAT = 2;


    @Override
    public Future<ExceptionWrapper> setPattern(int pattern) {
        byte[] cmd = new byte[2];

        cmd[0] = (byte) ('0' + pattern);
        return AsyncExecutor.execute(() -> {device.write(cmd, cmd.length);});
    }

    public Future<ExceptionWrapper> setPattern(int color, int pattern) {
        if (color == 0) {
            return setPattern(0);
        } else {
            return setPattern(pattern * 3 + color);
        }
    }
}
