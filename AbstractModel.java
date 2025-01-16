import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.HashMap;


public abstract class AbstractModel implements Model {

    private final Map<String, Object> attributes = new HashMap<>();
    
    
    @Override
    public void setAttribute(String name, Object value) {
        requireNonNull(name);
        requireNonNull(value);
        attributes.put(name, value);
    }   
    
    
    @Override
    public void removeAttribute(String name) {
        requireNonNull(name);
        attributes.remove(name);
    }
    
    
    @Override
    public Object getAttribute(String name) {
        requireNonNull(name);
        return attributes.get(name);
    }
}