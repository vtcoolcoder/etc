package dumbell.mvc.models;


import static java.util.Objects.requireNonNull;

import dumbell.enums.Indexes;
import java.util.Map;
import java.util.EnumMap;


public abstract class AbstractModel implements Model<Indexes, Double> {

    private final Map<Indexes, Double> attributes = new EnumMap<>(Indexes.class);
    
    
    @Override
    public void setAttribute(Indexes name, Double value) {
        requireNonNull(name);
        requireNonNull(value);
        attributes.put(name, value);
    }   
    
    
    @Override
    public void removeAttribute(Indexes name) {
        requireNonNull(name);
        attributes.remove(name);
    }
    
    
    @Override
    public Double getAttribute(Indexes name) {
        requireNonNull(name);
        return attributes.get(name);
    }
}