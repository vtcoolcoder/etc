import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.HashMap;


public class DumbBellModel {

    private final Map<String, Object> attributes = new HashMap<>();
    
    
    public void setAttribute(String name, Object value) {
        requireNonNull(name);
        requireNonNull(value);
        attributes.put(name, value);
    }   
    
    
    public void removeAttribute(String name) {
        requireNonNull(name);
        attributes.remove(name);
    }
    
    
    public Object getAttribute(String name) {
        requireNonNull(name);
        return attributes.get(name);
    }
}