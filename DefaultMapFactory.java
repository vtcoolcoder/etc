import static java.util.Objects.requireNonNull;
import static java.util.Map.Entry;

import java.util.Map;
import dumbell.enums.FuncCategories;


public class DefaultMapFactory implements MapFactory {

    private final Entry<Runnable, Runnable>[] entries;
    private final Runnable filler;
    private final Runnable shower;
    private final int row;
    
    
    @SuppressWarnings("unchecked")
    public DefaultMapFactory(int row,
                             Runnable filler,
                             Runnable shower,
                             Entry<Runnable, Runnable>... entries) {
        this.row = row;
        this.filler = requireNonNull(filler);
        this.shower = requireNonNull(shower);
        this.entries = requireNonNull(entries);
    }
    
    
    @SuppressWarnings("unchecked")
    public DefaultMapFactory(Runnable filler, 
                             Runnable shower, 
                             Entry<Runnable, Runnable>... entries) {
        this(Integer.MIN_VALUE, filler, shower, entries);
    }
    
    
    @Override 
    public Map<Integer, Map<FuncCategories, Runnable>> createAttachmentMap() {
        return createAttachmentMap(entries);
    }
    
    
    @Override
    public Entry<Integer, Map<FuncCategories, Runnable>> createMapEntry() {
        return createMapEntry(row, filler, shower);
    }
    
    
    @Override
    public Map<FuncCategories, Runnable> createEnumMap() {
        return createEnumMap(filler, shower);
    }
}