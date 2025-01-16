package dumbell.mvc.models;


public interface Model<K, V> {

    void setAttribute(K name, V value);
    
    void removeAttribute(K name);
    
    Object getAttribute(K name);
}