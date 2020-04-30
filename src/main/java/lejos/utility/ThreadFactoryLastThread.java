package lejos.utility;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryLastThread implements ThreadFactory {
    private Thread lastThread = null;

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        lastThread = t;
        return t;
    }

    public Thread getLastThread() {
        return lastThread;
    }
}
