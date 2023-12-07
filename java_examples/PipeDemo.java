import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class PipeDemo {
    @FunctionalInterface
    public interface Textable {
        StringBuffer process(StringBuffer input);
    }


    public static void main(String[] args) {
        runEcho(PipeDemo::wrapReverse);     
    }
    
    
    private static StringBuffer wrapReverse(StringBuffer item) { 
        return item != null ? item.reverse() : new StringBuffer();
    }
    
    
    private static void runEcho(Textable... funcs) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                processText(scanner.nextLine(), funcs)
                    .stream().forEach(System.out::println);
            }
        }
    }
    
    
    private static List<String> processText(String input, Textable... funcs) {
        List<String> result = new ArrayList<>();
        StringBuffer buffer = new StringBuffer(input != null ? input : "");
        
        for (Textable func : funcs) {
            result.add(func.process(buffer).toString());
        }
        
        return result;
    }
}