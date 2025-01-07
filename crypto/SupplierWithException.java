package crypto;

import java.util.function.Supplier;

@FunctionalInterface
public interface SupplierWithException<R, E extends Exception> {
    R get() throws E;
    
    default Supplier<R> wrapping() {
        return () -> {
            try {
                return get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}