package dumbell.observers;


@FunctionalInterface
public interface Observer<T> {

    void accept(T event);
}