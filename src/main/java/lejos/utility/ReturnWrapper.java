package lejos.utility;

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
}
