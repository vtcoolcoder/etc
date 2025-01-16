import static java.util.Objects.requireNonNull;
import static java.util.Map.Entry;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.EnumMap;
import java.util.Arrays;
import dumbell.enums.FuncCategories;


public interface MapFactory {

    Map<Integer, Map<FuncCategories, Runnable>> createAttachmentMap();
    
    
    Entry<Integer, Map<FuncCategories, Runnable>> createMapEntry();
    
    
    Map<FuncCategories, Runnable> createEnumMap();
    

    @SuppressWarnings("unchecked")
    default Map<Integer, Map<FuncCategories, Runnable>> 
    createAttachmentMap(Entry<Runnable, Runnable>... entries) {
        requireNonNull(entries);
        var entryList = Arrays.asList(entries);
        return entryList.stream()
                .map(this::createEnumMap)
                .map(map -> entry(entryList.indexOf(map)+1, map))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
    
    
    default Entry<Integer, Map<FuncCategories, Runnable>> 
    createMapEntry(int row, Runnable filler, Runnable shower) {
        return entry(row, createEnumMap(filler, shower));
    }
    
    
    default Map<FuncCategories, Runnable> createEnumMap(Entry<Runnable, Runnable> entry) {
        requireNonNull(entry);
        return createEnumMap(entry.getKey(), entry.getValue());
    }
    
    
    default Map<FuncCategories, Runnable> createEnumMap(Runnable filler, Runnable shower) {
        requireNonNull(filler);
        requireNonNull(shower);
        return new EnumMap<>(FuncCategories.class) {{ 
                put(FuncCategories.FILLING, filler); 
                put(FuncCategories.SHOWING, shower); 
        }};
    }
    
}