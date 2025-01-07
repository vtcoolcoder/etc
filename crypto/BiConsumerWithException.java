package crypto;

import java.util.function.BiConsumer;
import java.io.PrintWriter;

@FunctionalInterface
public interface BiConsumerWithException<F, S, E extends Exception> {
    void accept(F f, S s) throws E;
    
    
    default BiConsumer<F, S> wrapping() {
        return (f, s) -> {
            try {
                accept(f, s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    
    static void wrappingTryWithResources(
            BiConsumerWithException<PrintWriter, String, ? extends Exception> action,
            PrintWriter writer,
            String line
    ) {
        action.wrapping().accept(writer, line);
    }
    
    static void wrappingTryWithResources(PrintWriter writer, String line) {
        wrappingTryWithResources(
                (PrintWriter w, String l) -> { try (w) { w.print(l); }},
                writer, line);
    }
}