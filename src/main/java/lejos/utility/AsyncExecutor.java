package lejos.utility;

import lejos.internal.io.NativeDevice;

import java.util.concurrent.*;

/**
 * Helper to write tasks asyncronously (for example a command for a motor), no exceptions, no return values
 */
public class AsyncExecutor {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private AsyncExecutor() {
    }

    private static <T> Callable<ReturnWrapper<T>> wrap(Callable<T> toWrap) {
        return () -> {
            try {
                T ret = toWrap.call();
                return ReturnWrapper.newReturnWrapperNormal(ret);
            } catch (Exception e) {
                return ReturnWrapper.newReturnWrapperEx(e);
            }
        };
    }

    private static Callable<ExceptionWrapper> wrapVoid(Runnable toWrap) {
        return () -> {
            Exception ex;
            try {
                toWrap.run();
                ex = null;
            } catch (Exception e) {
                ex = e;
            }
            return ExceptionWrapper.newExceptionWrapper(ex);
        };
    }

    public static Future<ExceptionWrapper> execute(Runnable toExecute){
        return executor.submit(wrapVoid(toExecute));
    }

    public static <T> Future<ReturnWrapper<T>> execute(Callable<T> toExecute){
        return executor.submit(wrap(toExecute));
    }
}
