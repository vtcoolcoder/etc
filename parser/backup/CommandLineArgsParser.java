import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Arrays;


public class CommandLineArgsParser {
    private static class Params {
        private enum Names {
            LONG_NAME("--"),
            SHORT_NAME("-");
            
            private String paramName;
            private int paramSize;
            
            public String getName() { return paramName; }
            public int getSize() { return paramSize; }
            
            Names(String name) { 
                paramName = name; 
                paramSize = paramName.length();
            }
        }      
        
        
        private Params() {} 
            
            
        public static boolean isParamName(String arg) {
            return isValid(Names.SHORT_NAME, arg) || 
                   isValid(Names.LONG_NAME, arg);
        }
        
        
        public static String getParamName(String arg) {            
            String result = null;  
            
            for (Names param : Names.values()) {
                result = getCurrentName(param, arg);
                if (result != null) { return result; }
            }
            
            throw new ArgumentIsNotParameterException(NO_PARAM); 
        }      
       
        
        private static String getCurrentName(Names param, String arg) {
            if (isValid(param, arg)) {
                return getSubstring(param, arg);
            } else { return null; }
        }
       
        
        private static boolean isValid(Names param, String arg) {    
            return arg.startsWith(param.getName()); 
        }     
              
              
        private static String getSubstring(Names param, String arg) {
            return arg.substring(param.getSize());
        }      
    }
    
    
    private static final String NO_PARAM = "Аргумент командной строки не параметр!";
    private static final String NO_MATCH = "Заданное имя параметра не совпадает со списком допустимых!";   


    private static CommandLineArgsParser singletone;
    
    private static String[] commandLineArgs;
    private static Set<String> customParams;
    private static int clSize;
    private static String nextValue;
    private static Map<String, Set<String>> result = new LinkedHashMap<>();
    private static int counter = 0;
      
    
    public static CommandLineArgsParser newCommandLineArgsParser(String[] clArgs, 
                                                                 Set<String> customParams) {
        singletone = new CommandLineArgsParser(clArgs, customParams);
        return singletone;
    }
    
    
    public Map<String, Set<String>> getResult() { return result; }


    private CommandLineArgsParser(String[] clArgs, Set<String> customParams) {
        commandLineArgs = clArgs;
        clSize = commandLineArgs.length;
        this.customParams = customParams;
        streamParse();
    }
    
    
    private void streamParse() {
        Arrays.stream(commandLineArgs)
              .filter(Params::isParamName)
              .map(Params::getParamName)
              .filter(this::isMatchToCustomParamStream)
              .forEach(currentParamName -> { 
                  Set<String> currentParamNameValues = new LinkedHashSet<>();  
                  if (! (counter + 1 < clSize)) { return; }    
                  nextValue = commandLineArgs[counter + 1];       
                  counter += fillCurrentParamNameValues(currentParamNameValues, counter);
                  result.put(currentParamName, currentParamNameValues);
                  counter++;
              });
    }
    
      
    private boolean isMatchToCustomParamStream(String param) {
        if (customParams.contains(param)) {
            return true;
        } else { throw new MismatchParamException(NO_MATCH); }
    }
    
    
    private static int fillCurrentParamNameValues(Set<String> item, int i) { 
        int offset = 0;
                       
        for (int j = i + 1; isNextLoop(j); offset++) {
            String currentValue = commandLineArgs[j];       
            item.add(currentValue);    
            if (! (++j < clSize)) { break; }
            nextValue = commandLineArgs[j];
        }
        
        return offset;
    }
    
    
    private static boolean isNextLoop(int j) {
        return (! Params.isParamName(nextValue)) && (j < clSize);
    }
}