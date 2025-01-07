package crypto;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerWithException<T, E extends Exception> {
    void accept(T t) throws E;
    
    default Consumer<T> wrapping() {
        return t -> {
            try {
                accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}