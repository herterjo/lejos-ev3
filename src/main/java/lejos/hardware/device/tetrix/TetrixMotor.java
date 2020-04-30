package lejos.hardware.device.tetrix;

import lejos.robotics.DCMotor;
import lejos.utility.ReturnWrapper;

import java.util.concurrent.Future;

/** 
 * Tetrix basic DC motor abstraction without encoder support. The default power at instantiation is 100%.
 * <p>Use <code>{@link TetrixMotorController#getBasicMotor}</code> to retrieve a <code>TetrixMotor</code> instance.
 * 
 * @author Kirk P. Thompson
 */
public class TetrixMotor implements DCMotor{
    TetrixMotorController mc;
    int channel;
    
    TetrixMotor(TetrixMotorController mc, int channel) throws Exception {
        this.mc=mc;
        this.channel=channel;
        setPower(100);
    }

    public void setPower(int power) throws Exception {
        power=Math.abs(power);
        if (power>100) power=100;
        mc.doCommand(TetrixMotorController.CMD_SETPOWER, power, channel);
    }

    public int getPower() throws Exception {
        return mc.doCommand(TetrixMotorController.CMD_GETPOWER, 0, channel);
    }
    
    public void forward() throws Exception {
        mc.doCommand(TetrixMotorController.CMD_FORWARD, 0, channel);
    }

    public void backward() throws Exception {
        mc.doCommand(TetrixMotorController.CMD_BACKWARD, 0, channel);
    }

    public void stop() throws Exception {
        mc.doCommand(TetrixMotorController.CMD_STOP, 0, channel);
    }

    public void flt() throws Exception {
        mc.doCommand(TetrixMotorController.CMD_FLT, 0, channel);
    }
    
    /**
     * Return <code>true</code> if the motor is moving. Note that this method reports based on the current control
     * state (i.e. commanded to move) and not if the motor is actually moving. This means a motor may be stalled but this
     * method would return <code>true</code>.
     *
     * @return <code>true</code> if the motor is executing a movement command, <code>false</code> if stopped.
     */
    public Future<ReturnWrapper<Boolean>> isMoving() throws Exception {
        return ReturnWrapper.getCompletedReturnNormal(TetrixMotorController.MOTPARAM_OP_TRUE==mc.doCommand(TetrixMotorController.CMD_ISMOVING, 0, channel));
    }

    /** 
     * Used to alter the forward/reverse direction mapping for the motor output. This is primarily intended to
     * harmonize the forward and reverse directions for motors on opposite sides of a skid-steer chassis.
     * <p>
     * Changes to this setting take effect on the next motor command.
     * @param reverse <code>true</code> to reverse direction mapping for this motor
     */
    public void setReverse(boolean reverse) throws Exception {
        int op=TetrixMotorController.MOTPARAM_OP_FALSE;
        if (reverse) op=TetrixMotorController.MOTPARAM_OP_TRUE;
        mc.doCommand(TetrixMotorController.CMD_SETREVERSE, op, channel);
    }
}
