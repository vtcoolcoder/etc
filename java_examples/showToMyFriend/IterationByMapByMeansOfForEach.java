import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class IterationByMapByMeansOfForEach {
    public static void main(String[] args) {
        showMap(new HashMap<>(),
                new LinkedHashMap<>(),
                new TreeMap<>());
    }
    
    private static void showMap(Map<Integer, String>... maps) {
        for (Map<Integer, String> map : maps) {         
            map.put(9, "девять");
            map.put(8, "восемь");
            map.put(7, "семь");
            map.put(6, "шесть");
            map.put(5, "пять");
            map.put(4, "четыре");
            map.put(3, "три");
            map.put(2, "два");
            map.put(1, "один");
            map.put(0, "ноль");
        
            // Итерация по Map в стиле цикла for each
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            
            System.out.println();
        }
    }
}