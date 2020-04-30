package lejos.utility;

@FunctionalInterface
public interface RunExceptionReturn<T> {
    T run() throws Exception;
}
