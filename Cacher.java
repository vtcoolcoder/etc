

public interface Cacher<T> {

    T get();
    
    
    void update(T t);
    
    
    void reset();
}