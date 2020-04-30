package lejos.utility;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Helper to write tasks asyncronously (for example a command for a motor), no exceptions, no return values
 */
public class AsyncExecutor {
    private static final ThreadFactoryLastThread factory = new ThreadFactoryLastThread();
    private static final ExecutorService mainExecutor = Executors.newSingleThreadExecutor(factory);

    private AsyncExecutor() {
    }

    private static <T> Callable<ReturnWrapper<T>> wrap(RunExceptionReturn<T> toWrap) {
        return () -> {
            try {
                T ret = toWrap.run();
                return ReturnWrapper.newReturnWrapperNormal(ret);
            } catch (Exception e) {
                return ReturnWrapper.newReturnWrapperEx(e);
            }
        };
    }

    private static Callable<ExceptionWrapper> wrapVoid(RunException toWrap) {
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

    public static Future<ExceptionWrapper> execute(RunException toExecute) {
        Thread lastThread = factory.getLastThread();
        if (lastThread == null || lastThread.getId() != Thread.currentThread().getId()) {
            return mainExecutor.submit(wrapVoid(toExecute));
        }
        Exception ex;
        try {
            toExecute.run();
            ex = null;
        } catch (Exception e) {
            ex = e;
        }
        return ExceptionWrapper.getCompletedException(ex);
    }

    public static <T> Future<ReturnWrapper<T>> execute(RunExceptionReturn<T> toExecute) {
        Thread lastThread = factory.getLastThread();
        if (lastThread == null || lastThread.getId() != Thread.currentThread().getId()) {
            return mainExecutor.submit(wrap(toExecute));
        }
        Exception ex;
        T ret;
        try {
            ret = toExecute.run();
            ex = null;
        } catch (Exception e) {
            ex = e;
            ret = null;
        }
        if (ex != null) {
            return ReturnWrapper.getCompletedReturnException(ex);
        }
        return ReturnWrapper.getCompletedReturnNormal(ret);
    }

    public static void throwIfException(Future<ExceptionWrapper> toCheck) throws Exception {
        Exception ex = toCheck.get().getException();
        if (ex != null) {
            throw ex;
        }
    }
}
