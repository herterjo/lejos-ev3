package lejos.utility;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ExceptionWrapper {
    private final Exception exception;

    protected ExceptionWrapper(Exception ex) {
        this.exception = ex;
    }

    public Exception getException() {
        return exception;
    }

    public static ExceptionWrapper newExceptionWrapper(Exception ex) {
        return new ExceptionWrapper(ex);
    }

    public static Future<ExceptionWrapper> getCompletedException(Exception ex) {
        CompletableFuture<ExceptionWrapper> compFuture = new CompletableFuture<>();
        compFuture.complete(ExceptionWrapper.newExceptionWrapper(ex));
        return compFuture;
    }
}
