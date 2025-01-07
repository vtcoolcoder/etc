import java.util.Map;
import java.util.Set;
import java.util.Arrays;


public class CommandLineArgsParserTester {
    private static final CommandLineArgsParserTester MAIN_OBJ = 
        new CommandLineArgsParserTester("help", "verbose", "print", "version");
    
    private static Set<String> CUSTOM_PARAMS;
    private static final String FORMAT = "Имя параметра: %s | Значения:%n";
    
    public CommandLineArgsParserTester() {}
    
    public CommandLineArgsParserTester(String... customParams) {
        CUSTOM_PARAMS = Set.copyOf(Arrays.asList(customParams));
    }
   
    
    public static void main(String[] args) {
        //System.err.println(CUSTOM_PARAMS + "\n");
    
        CommandLineArgsParser parser =
            CommandLineArgsParser.newCommandLineArgsParser(args, CUSTOM_PARAMS);
            
        //System.err.println("\n" + parser.getResult() + "\n");
        
        for (Map.Entry<String, Set<String>> entry : parser.getResult().entrySet()) {
            String paramName = entry.getKey();
            Set<String> paramValues = entry.getValue();
            
            System.out.printf(FORMAT, paramName);
            
            for (String currentParamValue : paramValues) {
                System.out.println(currentParamValue);
            }
            
            System.out.println();
        }
    }
}