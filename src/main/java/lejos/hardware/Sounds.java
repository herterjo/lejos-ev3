package lejos.hardware;

public interface Sounds
{
    // Instruments (yes I know they don't sound anything like the names!)
    int[] PIANO = new int[]{4, 25, 500, 7000, 5};
    int[] FLUTE = new int[]{10, 25, 2000, 1000, 25};
    int[] XYLOPHONE = new int[]{1, 8, 3000, 5000, 5};

    int BEEP = 0;
    int DOUBLE_BEEP = 1;
    int ASCENDING = 2;
    int DESCENDING = 3;
    int BUZZ = 4;

    int VOL_MAX = 100;


}
