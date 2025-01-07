@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> {
    R apply(T t) throws E;
}

private static <T, R, E extends Exception> 
Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
    return arg -> {
        try {
            return fe.apply(arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
}