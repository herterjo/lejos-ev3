package lejos.utility;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ReturnWrapper<T> extends ExceptionWrapper {
    private final T value;

    protected ReturnWrapper(Exception ex, T value) {
        super(ex);
        this.value = value;
    }

    public static <Tm> ReturnWrapper<Tm> newReturnWrapperEx(Exception ex) {
        return new ReturnWrapper<Tm>(ex, null);
    }

    public static <Tm> ReturnWrapper<Tm> newReturnWrapperNormal(Tm val) {
        return new ReturnWrapper<Tm>(null, val);
    }

    public T getValue() throws Exception {
        Exception ex = getException();
        if (ex != null) {
            throw ex;
        }
        return value;
    }

    public static <Tm> Future<ReturnWrapper<Tm>> getCompletedReturnException(Exception ex) {
        CompletableFuture<ReturnWrapper<Tm>> compFuture = new CompletableFuture<>();
        compFuture.complete(ReturnWrapper.newReturnWrapperEx(ex));
        return compFuture;
    }

    public static <Tm> Future<ReturnWrapper<Tm>> getCompletedReturnNormal(Tm val) {
        CompletableFuture<ReturnWrapper<Tm>> compFuture = new CompletableFuture<>();
        compFuture.complete(ReturnWrapper.newReturnWrapperNormal(val));
        return compFuture;
    }
}
