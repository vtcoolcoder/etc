import static java.util.Comparator.*;
import static java.lang.String.CASE_INSENSITIVE_ORDER;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class TestingStreamFromScanner {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        var scanner = new Scanner(System.in).useDelimiter("\n");
        var stream = scanner.tokens()
                .distinct()
                .filter(e -> e.length() > 7)
                .sorted(CASE_INSENSITIVE_ORDER.reversed())
                .map(String::toUpperCase);                
        
        try (scanner; stream) {
            strings = stream.toList(); 
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            strings.forEach(System.out::println);
        }       
    }
}