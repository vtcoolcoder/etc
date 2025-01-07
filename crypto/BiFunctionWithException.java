package crypto;

import java.util.function.BiFunction;

@FunctionalInterface
public interface BiFunctionWithException<F, S, R, E extends Exception> {
    R apply(F f, S s) throws E;
    
    default BiFunction<F, S, R> wrapping() {
        return (f, s) -> {
            try {
                return apply(f, s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}