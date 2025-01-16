

public interface Model {

    void setAttribute(String name, Object value);
    
    void removeAttribute(String name);
    
    Object getAttribute(String name);
}