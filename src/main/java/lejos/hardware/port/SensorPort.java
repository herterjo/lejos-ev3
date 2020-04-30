package lejos.hardware.port;

import lejos.hardware.BrickFinder;

/**
 * Basic interface for EV3 sensor ports.
 * @author andy
 *
 */
public interface SensorPort {
    Port S1 = BrickFinder.getDefault().getPort("S1");
    Port S2 = BrickFinder.getDefault().getPort("S2");
    Port S3 = BrickFinder.getDefault().getPort("S3");
    Port S4 = BrickFinder.getDefault().getPort("S4");

}
