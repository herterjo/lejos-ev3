package lejos.internal.ev3;

import lejos.hardware.motor.MotorRegulator;
import lejos.hardware.port.BasicMotorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.internal.io.NativeDevice;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.utility.AsyncExecutor;
import lejos.utility.Delay;
import lejos.utility.ExceptionWrapper;
import lejos.utility.ReturnWrapper;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Abstraction for an EV3 output port.
 * <p>
 * TODO: Sort out a better way to do this, or least clean up the magic numbers.
 */
//TODO: Return Futures
public class EV3MotorPort extends EV3IOPort implements TachoMotorPort {
    static final byte OUTPUT_CONNECT = (byte) 1;
    static final byte OUTPUT_DISCONNECT = (byte) 2;
    static final byte OUTPUT_START = (byte) 4;
    static final byte OUTPUT_STOP = (byte) 5;
    static final byte OUTPUT_SET_TYPE = (byte) 6;
    static final byte OUTPUT_CLR_COUNT = (byte) 7;
    static final byte OUTPUT_POWER = (byte) 8;


    protected static byte[] regCmd2 = new byte[55 * 4];
    protected static NativeDevice tacho;
    protected static ByteBuffer bbuf;
    protected static IntBuffer ibuf;
    protected static IntBuffer ibufShadow;
    protected final static NativeDevice pwmDevice = new NativeDevice("/dev/lms_pwm");
    protected static Object syncedLock;

    static {
        initDeviceIO();
    }

    protected int curMode = FLOAT + 1; // current mode is unknown
    protected byte[] cmd = new byte[3];
    protected MotorRegulator regulator;
    protected static final EV3MotorRegulatorKernelModule[] syncSlave = new EV3MotorRegulatorKernelModule[0];

    /**
     * Implementation of a PID based motor regulator that uses a kernel module
     * for the core regulation operations. This mechanism is accessed via the
     * EV3MotorPort class.
     **/
    public class EV3MotorRegulatorKernelModule extends Thread implements MotorRegulator {
        static final int NO_LIMIT = 0x7fffffff;
        // Regulator move states
        static final int ST_IDLE = 0;
        static final int ST_STALL = 1;
        static final int ST_HOLD = 2;
        static final int ST_START = 3;
        static final int ST_ACCEL = 4;
        static final int ST_MOVE = 5;
        static final int ST_DECEL = 6;

        protected final int port;
        protected int zeroTachoCnt;
        protected int limitAngle;
        protected float curPosition;
        protected float curVelocity;
        protected float curCnt;
        protected int curTime;
        protected int curState;
        protected int curSerial;
        protected int curLimit;
        protected int curTachoCnt;
        protected float curSpeed;
        protected float curAcc;
        protected boolean curHold;
        protected boolean newMove;
        protected int stallLimit = 50;
        protected int stallTime = 1000;
        protected EV3MotorRegulatorKernelModule[] syncThis = new EV3MotorRegulatorKernelModule[]{this};
        protected EV3MotorRegulatorKernelModule[] syncWith = syncThis;
        protected EV3MotorRegulatorKernelModule[] syncActive = syncThis;

        protected byte[] regCmd = new byte[55];

        // state for listener stuff
        boolean started = false;
        RegulatedMotorListener listener;
        RegulatedMotor motor;

        public EV3MotorRegulatorKernelModule(TachoMotorPort p) {
            if (p != EV3MotorPort.this)
                throw new IllegalArgumentException("Invlaid port specified");
            // don't wait for the listener thread to finish
            this.setDaemon(true);
            // cache the actual port number
            this.port = EV3MotorPort.this.port;
        }

        // Fixed point routines and constants
        static final int FIX_SCALE = 256;

        protected int floatToFix(float f) {
            return Math.round(f * FIX_SCALE);
        }

        protected int intToFix(int i) {
            return i * FIX_SCALE;
        }

        protected float FixToFloat(int fix) {
            return (float) fix / FIX_SCALE;
        }

        protected int FixMult(int a, int b) {
            return (a * b) / FIX_SCALE;
        }

        protected int FixDiv(int a, int b) {
            return (a * FIX_SCALE) / b;
        }

        protected int FixRound(int a) {
            return (a >= 0 ? (a + FIX_SCALE / 2) / FIX_SCALE : (a - FIX_SCALE / 2) / FIX_SCALE);
        }


        /**
         * pack a value ready to be written to the kernel module
         *
         * @param buf
         * @param offset
         * @param val
         */
        protected void setVal(byte[] buf, int offset, int val) {
            buf[offset] = (byte) val;
            buf[offset + 1] = (byte) (val >> 8);
            buf[offset + 2] = (byte) (val >> 16);
            buf[offset + 3] = (byte) (val >> 24);
        }

        /**
         * Set the PID control parameters in the kernel module
         *
         * @param typ
         * @param moveP
         * @param moveI
         * @param moveD
         * @param holdP
         * @param holdI
         * @param holdD
         * @param offset
         * @param deadBand
         */
        public synchronized Future<ExceptionWrapper> setControlParams(int typ, float moveP, float moveI, float moveD, float holdP, float holdI, float holdD, int offset, float deadBand) {
            regCmd[0] = OUTPUT_SET_TYPE;
            regCmd[1] = (byte) port;
            regCmd[2] = (byte) typ;
            setVal(regCmd, 3, floatToFix(moveP));
            setVal(regCmd, 7, floatToFix(moveI));
            setVal(regCmd, 11, floatToFix(moveD));
            setVal(regCmd, 15, floatToFix(holdP));
            setVal(regCmd, 19, floatToFix(holdI));
            setVal(regCmd, 23, floatToFix(holdD));
            setVal(regCmd, 27, offset);
            setVal(regCmd, 31, floatToFix(deadBand));
            byte[] newCMD = new byte[35];
            System.arraycopy(regCmd, 0, newCMD, 0, 35);
            return AsyncExecutor.execute(() -> {
                pwmDevice.write(newCMD, 35);
            });
        }


        /**
         * Check to see if the current command is complete and if needed call
         * any listeners.
         */
        protected synchronized Future<ExceptionWrapper> checkComplete() {
            if (!started) {
                return ExceptionWrapper.getCompletedException(null);
            }
            return AsyncExecutor.execute(() -> {
                boolean isMoving = isMoving().get().getValue();
                if (!isMoving) {
                    started = false;
                    if (listener != null) {
                        boolean isStalled = isStalled().get().getValue();
                        int tachoCount = getTachoCount().get().getValue();
                        listener.rotationStopped(motor, tachoCount, isStalled, System.currentTimeMillis());
                    }
                }
            });
        }

        /**
         * We are starting a new move operation. Handle listeners as required
         */
        protected synchronized Future<ExceptionWrapper> startNewMove() {
            return AsyncExecutor.execute(() -> {
                if (started)
                    AsyncExecutor.throwIfException(checkComplete());
                if (started)
                    throw new IllegalStateException("Motor must be stopped");
                started = true;
                if (listener != null) {
                    int tachoCount = getTachoCount().get().getValue();
                    listener.rotationStarted(motor, tachoCount, false, System.currentTimeMillis());
                    notifyAll();
                }
            });
        }

        /**
         * Thread to handle listeners.
         */
        public synchronized void run() {
            while (true) {
                // wait until a move is actually started
                while (!started)
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                try {
                    checkComplete().get();
                } catch (Exception e) {
                }
                try {
                    wait(5);
                } catch (InterruptedException e) {
                }
            }
        }

        /**
         * Start a move using the PID loop in the kernel module
         *
         * @param t1   Time for acceleration phase
         * @param t2   Time for cruise phase
         * @param t3   Time for deceleration phase
         * @param c2   Position (cnt) after acceleration phase
         * @param c3   Position (cnt) after cruise stage
         * @param v1   Velocity at start of acceleration stage
         * @param v2   Velocity after acceleration stage
         * @param a1   Acceleration
         * @param a3   Deceleration
         * @param sl   stall limit
         * @param st   stall time
         * @param ts   Time stamp
         * @param hold What to do after the move
         */
        protected synchronized Future<ExceptionWrapper> subMove(int t1, int t2, int t3, float c1, float c2, float c3, float v1, float v2, float a1, float a3, int sl, int st, int ts, boolean hold) {
            //System.out.println("t1 " + t1 + " t2 " + t2 + " t3 " + t3 + " c1 " + c1 + " c2 " + c2 + " c3 " + c3 + " v1 " + v1 + " v2 " + v2 + " a1 " + a1 + " a3 " + a3);
            // convert units from /s (i.e 100ms) to be per 1024ms to allow div to be performed by shift
            v1 = (v1 / 1000f) * 1024f;
            v2 = (v2 / 1000f) * 1024f;
            a1 = (((a1 / 1000f) * 1024f) / 1000f) * 1024f;
            a3 = (((a3 / 1000f) * 1024f) / 1000f) * 1024f;
            // now start the actual move
            regCmd[0] = OUTPUT_START;
            regCmd[1] = (byte) port;
            setVal(regCmd, 2, t1);
            setVal(regCmd, 6, t2);
            setVal(regCmd, 10, t3);
            setVal(regCmd, 14, floatToFix(c1));
            setVal(regCmd, 18, floatToFix(c2));
            setVal(regCmd, 22, floatToFix(c3));
            setVal(regCmd, 26, floatToFix(v1));
            setVal(regCmd, 30, floatToFix(v2));
            setVal(regCmd, 34, floatToFix(a1));
            setVal(regCmd, 38, floatToFix(a3));
            setVal(regCmd, 42, sl);
            setVal(regCmd, 46, st);
            setVal(regCmd, 50, ts);
            regCmd[54] = (byte) (hold ? 1 : 0);
            // if we are going to move then tell any listeners.
            if ((v1 != 0 || v2 != 0) && ts == 0)
                return startNewMove();
            else {
                return ExceptionWrapper.getCompletedException(null);
            }
        }

        /**
         * Helper method generate a move by splitting it into three phases, initial
         * acceleration, constant velocity, and final deceleration. We allow for the case
         * were it is not possible to reach the required constant velocity and hence the
         * move becomes triangular rather than trapezoid.
         *
         * @param curVel Initial velocity
         * @param curPos Initial position
         * @param speed
         * @param acc
         * @param limit
         * @param hold
         */
        protected Future<ExceptionWrapper> genMove(float curVel, float curPos, float curCnt, int curTime, float speed, float acc, int limit, boolean hold) {
            // Save current move params we may need these to adjust speed etc.
            float u2 = curVel * curVel;
            //int len = (int)(limit - curPos);
            float len = (limit - curPos);
            float v = speed;
            float a1 = acc;
            float a3 = acc;
            //System.out.println("pos " + curPos + " curVel " + curVel + " limit " + limit + " len " + len + " speed " + speed + " hold " + hold);
            if (speed == 0.0) {
                // Stop case
                //System.out.println("Stop");
                if (curVel < 0)
                    a3 = -acc;
                int t3 = (int) (1000 * (curVel / a3));
                return subMove(0, 0, t3, 0, 0, curCnt, 0, curVel, 0, -a3, stallLimit, stallTime, curTime, hold);
            }
            float v2 = v * v;
            if (Math.abs(limit) == NO_LIMIT) {
                // Run forever, no need for deceleration at end
                //System.out.println("Unlimited move");
                if (limit < 0)
                    v = -speed;
                if (v < curVel)
                    a1 = -acc;
                float s1 = (v2 - u2) / (2 * a1);
                int t1 = (int) (1000 * (v - curVel) / a1);
                return subMove(t1, NO_LIMIT, 0, curCnt, curCnt + s1, 0, curVel, v, a1, 0, stallLimit, stallTime, curTime, hold);
            }
            // We have some sort of target position work out how to get to it
            if (curVel != 0) {
                // we need to work out if we can get to the end point in a single move
                if (curVel < 0)
                    a3 = -acc;
                float s3 = (u2) / (2 * a3);
                //System.out.println("stop pos " + s3);
                // if final position is less than stop pos we need to reverse direction
                if (len < s3)
                    v = -speed;
                a3 = acc;
            } else if (len < 0)
                v = -speed;
            if (v < curVel)
                a1 = -acc;
            if (v < 0)
                a3 = -acc;
            float vmax2 = a3 * len + u2 / 2;
            // can we ever reach target velocity?
            if (vmax2 <= v2) {
                // triangular move
                //System.out.println("Triangle");
                if (vmax2 < 0) System.out.println("vmax -ve" + vmax2);
                if (v < 0)
                    v = -(float) Math.sqrt(vmax2);
                else
                    v = (float) Math.sqrt(vmax2);
                float s1 = (vmax2 - u2) / (2 * a1);
                int t1 = (int) (1000 * (v - curVel) / a1);
                int t2 = t1;
                int t3 = t2 + (int) (1000 * (v / a3));
                return subMove(t1, t2, t3, curCnt, 0, s1 + curCnt, curVel, v, a1, -a3, stallLimit, stallTime, curTime, hold);
            } else {
                // trapezoid move
                //System.out.println("Trap");
                float s1 = (v2 - u2) / (2 * a1);
                float s3 = (v2) / (2 * a3);
                float s2 = len - s1 - s3;
                //System.out.println("s1 " + s1 + " s2 " + s2 + " s3 " + s3);
                int t1 = (int) (1000 * (v - curVel) / a1);
                int t2 = t1 + (int) (1000 * s2 / v);
                int t3 = t2 + (int) (1000 * (v / a3));
                //System.out.println("v " + v + " a1 " + a1 + " a3 " + (-a3));
                return subMove(t1, t2, t3, curCnt, curCnt + s1, curCnt + s1 + s2, curVel, v, a1, -a3, stallLimit, stallTime, curTime, hold);
            }
        }

        /**
         * Waits for the current move operation to complete
         *
         * @return
         */
        public synchronized Future<ExceptionWrapper> waitComplete() {
            return AsyncExecutor.execute(() -> {
                for (EV3MotorRegulatorKernelModule r : syncActive) {
                    boolean isMoving = r.isMoving().get().getValue();
                    while (isMoving) {
                        Delay.msDelay(1);
                        isMoving = r.isMoving().get().getValue();
                    }
                }
                for (EV3MotorRegulatorKernelModule r : syncActive) {
                    AsyncExecutor.throwIfException(r.checkComplete());
                }
            });
        }

        protected synchronized Future<ExceptionWrapper> executeMove() {
            List<Future<ExceptionWrapper>> checkers = new LinkedList<>();
            // first generate all of the active moves
            for (EV3MotorRegulatorKernelModule r : syncActive) {
                if (r.newMove) {
                    checkers.add(r.genMove(r.curVelocity, r.curPosition, r.curCnt, (r.curState >= ST_START ? r.curTime : 0),
                            r.curSpeed, r.curAcc, r.curLimit, r.curHold));
                }

            }
            // now write them to the kernel
            synchronized (syncedLock) {
                int cntLen = 0;
                for (EV3MotorRegulatorKernelModule r : syncActive) {
                    if (r.newMove) {
                        System.arraycopy(r.regCmd, 0, regCmd2, cntLen, 55);
                        cntLen += 55;
                        //pwm.write(r.regCmd, 55);
                        r.newMove = false;
                    }
                }
                int lenCpyBecauseOfFinal = cntLen;
                byte[] newCMD = new byte[lenCpyBecauseOfFinal];
                System.arraycopy(regCmd2, 0, newCMD, 0, lenCpyBecauseOfFinal);
                return AsyncExecutor.execute(() -> {
                    for (Future<ExceptionWrapper> c : checkers) {
                        AsyncExecutor.throwIfException(c);
                    }
                    pwmDevice.write(newCMD, lenCpyBecauseOfFinal);
                });
            }
        }

        /**
         * Initiate a new move and optionally wait for it to complete.
         * If some other move is currently executing then ensure that this move
         * is terminated correctly and then start the new move operation.
         *
         * @param speed
         * @param acceleration
         * @param limit
         * @param hold
         * @param waitComplete
         * @return
         */
        public synchronized Future<ExceptionWrapper> newMove(float speed, int acceleration, int limit, boolean hold, boolean waitComplete) {
            limitAngle = limit;
            if (Math.abs(limit) != NO_LIMIT)
                limit += zeroTachoCnt;
            int limitCopy = limit;
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    AsyncExecutor.throwIfException(updateRegulatorInformation());
                    // Ignore repeated commands
                    if (curState != ST_STALL && !waitComplete && (speed == curSpeed)
                            && (curAcc == acceleration) && (curLimit == limitCopy) && (curHold == hold)) {
                        return;
                    }
                    // save the move parameters
                    curSpeed = speed;
                    curHold = hold;
                    curAcc = acceleration;
                    curLimit = limitCopy;
                    newMove = true;
                    AsyncExecutor.throwIfException(executeMove());
                    if (waitComplete) {
                        AsyncExecutor.throwIfException(waitComplete());
                    }
                }
            });
        }

        /**
         * The kernel module updates the shared memory serial number every time
         * a new command is issued. We can use this to wait for the shared mem
         * to be updated. We must do this to ensure that we do not see a move
         * as complete when in fact it may not have even started yet!
         *
         * @return
         */
        protected synchronized Future<ReturnWrapper<Integer>> getSerialNo() {
            return AsyncExecutor.execute(() -> {
                synchronized (ibuf) {
                    return ibuf.get(port * 8 + 7);
                }
            });
        }

        /**
         * Grabs the current state of the regulator and stores in class
         * member variables
         */
        protected synchronized Future<ExceptionWrapper> updateRegulatorInformation() {
            return AsyncExecutor.execute(() -> {
                int time;
                int time2;
                // if there are no active regulators nothing to do
                if (syncActive.length <= 0) return;
                synchronized (ibufShadow) {
                    // Check to make sure time is not changed during read
                    do {
                        // TODO: sort out how to handle JIT issues and shared memory.
                        // The problem is that when the JIT compiler gets to work
                        // it ends up seeing the shared memory as a simple array.
                        // It is not possible to label this array as volatile so
                        // some of the following code is seen as invariant and so can be
                        // optimised. Adding the synchronized section seems to help
                        // with this but it is not ideal. Need a better solution if possible
                        synchronized (ibuf) {
                            // copy the main buffer to the shadow to freeze the state
                            ibuf.rewind();
                            time = ibuf.get(port * 8 + 5);
                            ibuf.get(ibufShadow.array());
                            time2 = ibuf.get(port * 8 + 6);
                        }
                    } while (time != time2);
                    // now cache the values in the active regulators
                    for (EV3MotorRegulatorKernelModule r : syncActive) {
                        synchronized (r) {
                            final int base = r.port * 8;
                            r.curCnt = FixToFloat(ibufShadow.get(base + 1));
                            r.curPosition = r.curCnt + ibufShadow.get(base);
                            r.curVelocity = (FixToFloat(ibufShadow.get(base + 2)) / 1024) * 1000;
                            r.curTime = ibufShadow.get(base + 5);
                            r.curState = ibufShadow.get(base + 4);
                            r.curTachoCnt = ibufShadow.get(base + 3) - zeroTachoCnt;
                            r.curSerial = ibufShadow.get(base + 7);
                        }
                    }
                }
            });
        }

        /**
         * returns the current position from the regulator
         *
         * @return current position in degrees
         */
        public synchronized Future<ReturnWrapper<Float>> getPosition() {
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    AsyncExecutor.throwIfException(updateRegulatorInformation());
                    return curPosition - zeroTachoCnt;
                }
            });
        }

        /**
         * returns the current velocity from the regulator
         *
         * @return velocity in degrees per second
         */
        public synchronized Future<ReturnWrapper<Float>> getCurrentVelocity() {
            return AsyncExecutor.execute(() -> {
                AsyncExecutor.throwIfException(updateRegulatorInformation());
                synchronized (this) {
                    return curVelocity;
                }
            });
        }


        /**
         * return the regulator state.
         *
         * @return
         */
        protected synchronized Future<ReturnWrapper<Integer>> getRegState() {
            if (syncActive.length <= 0) return ReturnWrapper.getCompletedReturnNormal(curState);
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    curState = ibuf.get(port * 8 + 4);
                    return curState;
                }
            });
        }

        public synchronized Future<ReturnWrapper<Boolean>> isMoving() {
            return AsyncExecutor.execute(() -> getRegState().get().getValue() >= ST_START);
        }

        public synchronized Future<ReturnWrapper<Boolean>> isStalled() {
            return AsyncExecutor.execute(() -> getRegState().get().getValue() == ST_STALL);
        }

        public synchronized Future<ReturnWrapper<Integer>> getTachoCount() {
            if (syncActive.length <= 0) return ReturnWrapper.getCompletedReturnNormal(curTachoCnt);
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    return EV3MotorPort.this.getTachoCount().get().getValue() - zeroTachoCnt;
                }
            });
        }

        public synchronized Future<ExceptionWrapper> resetTachoCount() {
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    zeroTachoCnt = EV3MotorPort.this.getTachoCount().get().getValue();
                }
            });
        }


        public synchronized void setStallThreshold(int error, int time) {
            this.stallLimit = error;
            this.stallTime = time;
        }


        /**
         * The target speed has been changed. Reflect this change in the
         * regulator.
         *
         * @param newSpeed new target speed.
         * @return
         */
        public synchronized Future<ExceptionWrapper> adjustSpeed(float newSpeed) {
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    if (curSpeed != 0 && newSpeed != curSpeed) {
                        adjust(true, newSpeed);
                    }
                }
            });
        }

        private synchronized void adjust(boolean speed, float valToSet) throws Exception {
            AsyncExecutor.throwIfException(updateRegulatorInformation());
            if (curState >= ST_START && curState <= ST_MOVE) {
                if (speed) {
                    curSpeed = valToSet;
                } else {
                    curAcc = valToSet;
                }
                newMove = true;
                AsyncExecutor.throwIfException(executeMove());
            }
        }

        /**
         * The target acceleration has been changed. Updated the regulator.
         *
         * @param newAcc
         * @return
         */
        public synchronized Future<ExceptionWrapper> adjustAcceleration(int newAcc) {
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    if (newAcc != curAcc) {
                        adjust(false, newAcc);
                    }
                }
            });
        }


        @Override
        public synchronized Future<ExceptionWrapper> setControlParamaters(int typ, float moveP, float moveI,
                                                                          float moveD, float holdP, float holdI,
                                                                          float holdD, int offset) {
            return setControlParams(typ, moveP, moveI, moveD, holdP, holdI, holdD, offset, 0.5f);
        }


        @Override
        public synchronized void addListener(RegulatedMotor motor, RegulatedMotorListener listener) {
            this.motor = motor;
            this.listener = listener;
            if (getState() == Thread.State.NEW)
                start();
        }


        @Override
        public synchronized RegulatedMotorListener removeListener() {
            RegulatedMotorListener old = listener;
            listener = null;
            return old;
        }


        @Override
        public synchronized int getLimitAngle() {
            return limitAngle;
        }

        public synchronized void synchronizeWith(MotorRegulator[] syncList) {
            // validate the list
            for (MotorRegulator r : syncList) {
                if (!(r instanceof EV3MotorRegulatorKernelModule))
                    throw new IllegalArgumentException("Invalid regulator class - is it remote?");
                if (r == this)
                    throw new IllegalArgumentException("Can't synchronize with self");
            }
            // create new array and add self into it
            EV3MotorRegulatorKernelModule[] sl = new EV3MotorRegulatorKernelModule[syncList.length + 1];
            int i = 1;
            for (MotorRegulator r : syncList)
                sl[i++] = (EV3MotorRegulatorKernelModule) r;
            sl[0] = this;
            this.syncWith = sl;
        }

        public synchronized Future<ExceptionWrapper> startSynchronization() {
            synchronized (syncedLock) {
                // set slaves to sync
                for (int i = 1; i < syncWith.length; i++)
                    syncWith[i].syncActive = syncSlave;
                this.syncActive = this.syncWith;
                Future<ExceptionWrapper> updateFuture = updateRegulatorInformation();
                this.syncActive = syncSlave;
                return updateFuture;
            }
        }

        public synchronized Future<ExceptionWrapper> endSynchronization(boolean immRet) {
            return AsyncExecutor.execute(() -> {
                synchronized (this) {
                    // execute all synchronized operations
                    syncActive = syncWith;
                    AsyncExecutor.throwIfException(executeMove());
                    // reset operations back to normal for slaves
                    for (int i = 1; i < syncWith.length; i++)
                        syncWith[i].syncActive = syncWith[i].syncThis;
                    if (!immRet)
                        AsyncExecutor.throwIfException(waitComplete());
                    // set master back to normal operation
                    syncActive = syncThis;
                }
            });
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public synchronized Future<ReturnWrapper<Boolean>> open(int typ, int port, EV3Port ref) {
        return AsyncExecutor.execute(() -> {
            Future<ReturnWrapper<Boolean>> opened = super.open(typ, port, ref);
            if (!opened.get().getValue())
                return false;
            byte[] newcmd = new byte[2];
            synchronized (this) {
                cmd[0] = OUTPUT_CONNECT;
                cmd[1] = (byte) port;
                System.arraycopy(cmd, 0, newcmd, 0, 2);
            }
            pwmDevice.write(newcmd, 2);
            return true;
        });
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public synchronized Future<ExceptionWrapper> closeRet() {
        cmd[0] = OUTPUT_DISCONNECT;
        cmd[1] = (byte) port;
        byte[] newcmd = new byte[2];
        System.arraycopy(cmd, 0, newcmd, 0, 2);
        return AsyncExecutor.execute(() -> {
            pwmDevice.write(newcmd, 2);
            super.close();
        });
    }

    private void closePrivate(byte[] newcmd) throws Exception {

    }


    /**
     * Helper method to adjust the requested power
     *
     * @param power
     */
    protected synchronized Future<ExceptionWrapper> setPower(int power) {
        cmd[0] = OUTPUT_POWER;
        cmd[1] = (byte) port;
        cmd[2] = (byte) power;
        byte[] newcmd = new byte[3];
        System.arraycopy(cmd, 0, newcmd, 0, 3);
        return AsyncExecutor.execute(() -> {
            pwmDevice.write(newcmd, 3);
        });
    }

    /**
     * Helper method stop the motor
     *
     * @param flt
     */
    protected synchronized Future<ExceptionWrapper> stop(boolean flt) {
        cmd[0] = OUTPUT_STOP;
        cmd[1] = (byte) port;
        cmd[2] = (byte) (flt ? 0 : 1);
        byte[] newcmd = new byte[3];
        System.arraycopy(cmd, 0, newcmd, 0, 3);
        return AsyncExecutor.execute(() -> {
            pwmDevice.write(newcmd, 3);
        });
    }


    /**
     * Low-level method to control a motor.
     *
     * @param power power from 0-100
     * @param mode  defined in <code>BasicMotorPort</code>. 1=forward, 2=backward, 3=stop, 4=float.
     * @return
     * @see BasicMotorPort#FORWARD
     * @see BasicMotorPort#BACKWARD
     * @see BasicMotorPort#FLOAT
     * @see BasicMotorPort#STOP
     */
    public synchronized Future<ExceptionWrapper> controlMotor(int power, int mode) {
        Future<ExceptionWrapper> ret;
        // Convert lejos power and mode to EV3 power and mode
        if (mode >= STOP) {
            power = 0;
            ret = stop(mode == FLOAT);
        } else {
            if (mode == BACKWARD)
                power = -power;
            ret = setPower(power);
        }
        curMode = mode;
        return ret;
    }


    /**
     * returns tachometer count
     *
     * @return
     */
    public synchronized Future<ReturnWrapper<Integer>> getTachoCount() {
        return AsyncExecutor.execute(() -> {
            synchronized (ibuf) {
                return ibuf.get(port * 8 + 3);
            }
        });
    }


    /**
     * resets the tachometer count to 0;
     *
     * @return
     */
    public synchronized Future<ExceptionWrapper> resetTachoCount() {
        cmd[0] = OUTPUT_CLR_COUNT;
        cmd[1] = (byte) port;
        byte[] newcmd = new byte[2];
        System.arraycopy(cmd, 0, newcmd, 0, 2);
        return AsyncExecutor.execute(() -> {
            pwmDevice.write(newcmd, 2);
        });
    }

    public void setPWMMode(int mode) {
    }


    private synchronized static void initDeviceIO() {
        tacho = new NativeDevice("/dev/lms_motor");
        bbuf = tacho.mmap(4 * 8 * 4).getByteBuffer(0, 4 * 8 * 4);
        //System.out.println("direct " + bbuf.isDirect());
        ibuf = bbuf.asIntBuffer();
        // allocate the shadow buffer
        ibufShadow = IntBuffer.allocate(4 * 8);
        syncedLock = new Object();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized MotorRegulator getRegulator() {
        if (regulator == null)
            regulator = new EV3MotorRegulatorKernelModule(this);
        //regulator = new JavaMotorRegulator(this);
        return regulator;
    }
}
