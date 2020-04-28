package lejos.internal.ev3;

import lejos.hardware.LED;
import lejos.internal.io.AsyncSender;

public class EV3LED implements LED {
    private static final String ledpath = "/dev/lms_ui";
    public static int COLOR_NONE = 0;
    public static int COLOR_GREEN = 1;
    public static int COLOR_RED = 2;
    public static int COLOR_ORANGE = 3;
    public static int PATTERN_ON = 0;
    public static int PATTERN_BLINK = 1;
    public static int PATTERN_HEARTBEAT = 2;


    @Override
    public void setPattern(int pattern) {
        byte[] cmd = new byte[2];

        cmd[0] = (byte) ('0' + pattern);
        AsyncSender.getInstance().write(cmd, cmd.length, ledpath);
    }

    public void setPattern(int color, int pattern) {
        if (color == 0) {
            setPattern(0);
        } else {
            setPattern(pattern * 3 + color);
        }
    }
}