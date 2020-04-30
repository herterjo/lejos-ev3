package lejos.utility;

@FunctionalInterface
public interface RunExceptionReturn<T> {
    public T run() throws Exception;
}
