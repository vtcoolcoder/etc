import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class StreamTest
{
    private static ArrayList<String> names = new ArrayList<>(
        Arrays.asList(
            "Алёна", 
            "Резеда", 
            "Жанна", 
            "Яна"
        ));
    
    public static void main(String[] args)
    {
        names.stream().forEach(System.out::println);
        
        Predicate<String> filtered = s -> s.equals("Алёна");
        Predicate<String> combo = filtered.or(s -> s.equals("Жанна"));
        
        System.out.println(
            names.stream()
                 .filter(combo)
                 .collect(Collectors.joining(", ")));
                          
        List<Integer> lengths = 
            names.stream()
                 .map(String::length)
                 .collect(Collectors.toList());
                 
        System.out.println(lengths);                           
    }
}
