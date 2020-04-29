package lejos.utility;

public class ExceptionWrapper {
    private final Exception exception;

    protected ExceptionWrapper(Exception ex) {
        this.exception = ex;
    }

    public Exception getException() {
        return exception;
    }

    public static ExceptionWrapper newExceptionWrapper(Exception ex){
        return new ExceptionWrapper(ex);
    }
}
