

public class CacherImpl<T> implements Cacher<T> {

    private T t;
    
    private final T initValue;
    
    
    public CacherImpl(T t, T initValue) { 
        this.t = t; 
        this.initValue = initValue;
    }    
    
  
    public CacherImpl(T initValue) { this.initValue = initValue; }
    
    
    @Override
    public T get() { return t; }
    
    
    @Override
    public void update(T t) { this.t = t; }
    
    
    @Override
    public void reset() { t = initValue; }
}