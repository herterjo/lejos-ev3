package lejos.hardware.motor;

import lejos.hardware.BrickFinder;

/**
 * Motor class contains 3 instances of regulated motors.
 * <p>
 * Example:<p>
 * <code><pre>
 *   Motor.A.setSpeed(720);// 2 RPM
 *   Motor.C.setSpeed(720);
 *   Motor.A.forward();
 *   Motor.C.forward();
 *   Thread.sleep (1000);
 *   Motor.A.stop();
 *   Motor.C.stop();
 *   Motor.A.rotateTo( 360);
 *   Motor.A.rotate(-720,true);
 *   while(Motor.A.isMoving() :Thread.yield();
 *   int angle = Motor.A.getTachoCount(); // should be -360
 *   LCD.drawInt(angle,0,0);
 * </pre></code>
 * @author Roger Glassey/Andy Shaw
 */
public class Motor
{
    /**
     * Motor A.
     */
    public static NXTRegulatedMotor A;

    /**
     * Motor B.
     */
    public static NXTRegulatedMotor B;
    /**
     * Motor C.
     */
    public static NXTRegulatedMotor C;

    /**
     * Motor D.
     */
    public static NXTRegulatedMotor D;

    static {
        try {
            A = new NXTRegulatedMotor(BrickFinder.getDefault().getPort("A"));
            B = new NXTRegulatedMotor(BrickFinder.getDefault().getPort("B"));
            C = new NXTRegulatedMotor(BrickFinder.getDefault().getPort("C"));
            D = new NXTRegulatedMotor(BrickFinder.getDefault().getPort("D"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Motor() {
    	// Motor class cannot be instantiated
    }
    
}
