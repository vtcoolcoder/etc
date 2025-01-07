package parser;

import java.util.Map;
import java.util.Set;
import java.util.Arrays;


public class CommandLineArgsParserTester {
    private static CommandLineArgsParserTester MAIN_OBJ; 
        
    
    private static final String[] DEFAULT = { "help", "verbose", "print", "version" };
    private static final String FORMAT = "Имя параметра: %s | Значения:%n";
    
    private static Set<String> CUSTOM_PARAMS;
    
    public CommandLineArgsParserTester() { this(DEFAULT); }
    
    public CommandLineArgsParserTester(String[] customParams) {
        CUSTOM_PARAMS = Set.copyOf(Arrays.asList(customParams));
    }
   
    
    public static void main(String[] args) {
        MAIN_OBJ = new CommandLineArgsParserTester();
    
        CommandLineArgsParser parser =
            CommandLineArgsParser.newCommandLineArgsParser(args, CUSTOM_PARAMS);
            
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