package crypto;

@FunctionalInterface
public interface RunnableWithException<E extends Exception> {
    void run() throws E;
    
    default Runnable wrapping() {
        return () -> {
            try {
                run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}