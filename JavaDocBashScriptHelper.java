import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Supplier;


public record JavaDocBashScriptHelper(String[] names, String formatter) {
          
    private record GetWrappedRawParams(
            String wrappingRaw, 
            String formatter, 
            boolean hasPrefix, 
            boolean isEndingSuffix
    ) {
        public GetWrappedRawParams {
            Objects.requireNonNull(wrappingRaw);
            Objects.requireNonNull(formatter);
        }
    }
    
    
    private record SelectWrappedRawParams(boolean isByDefault, GetWrappedRawParams nested) {
        public SelectWrappedRawParams {
            Objects.requireNonNull(nested);
        }
    }


    private static final String PREFIX = " ".repeat(4);
    private static final String ENDING_SUFFIX = ";";
    private static final String DEFAULT_SUFFIX = " || \\";
    private static final String FORMATTER = 
            PREFIX + "javadoc -d docs -sourcepath \"$i\" -subpackages %s";
                       
    private static final String[] NAMES = {
            "java", 
            "javax", 
            "org", 
            "com", 
            "sun", 
            "netscape", 
            "jdk" 
    };
    
    
    public static void main(String[] args) {
        generateByDefault();
    }
    
       
    public JavaDocBashScriptHelper(String[] names) {
        this(names, "");
    }    
    
    
    public JavaDocBashScriptHelper {
        Objects.requireNonNull(names);
        Objects.requireNonNull(formatter);
    }
    
    
    public static JavaDocBashScriptHelper newInstance(String[] names) {
        return new JavaDocBashScriptHelper(names);
    }
    
    
    public static JavaDocBashScriptHelper newExtraInstance(String[] names, String formatter) {
        return new JavaDocBashScriptHelper(names, formatter);
    }
    
    
    public void generate() {
        generate((int) Math.pow(2, names.length), i -> map(i, names), formatter);
    }
    
    
    public static void generateByDefault() {
        generate((int) Math.pow(2, NAMES.length));
    }
    
    
    private static void generate(int bound) {
        generate(bound, JavaDocBashScriptHelper::map, "");
    }
    
    
    private static void generate(int bound, IntFunction<String> func, String formatter) {
        System.out.println("generateJavaDocs() {");
        
        for (int i = 1; i < bound; i++) {
            var isEnding = bound - 1 == i;
            var isFirstLine = 1 == i;
            var wrappingRaw = func.apply(i);
            var selectWrappedRawArgs = new SelectWrappedRawParams(
                    formatter.isEmpty(),
                    new GetWrappedRawParams(
                            wrappingRaw,
                            formatter,
                            !isFirstLine,
                            isEnding
            ));
            
            System.out.println(selectWrappedRaw(selectWrappedRawArgs));
        }
        
        System.out.println("}");
    }
    
    
    private static String selectWrappedRaw(SelectWrappedRawParams params) {
        Supplier<GetWrappedRawParams> getWrappedRawDefaultArgs = () -> new GetWrappedRawParams(
                params.nested().wrappingRaw(), 
                FORMATTER, 
                params.nested().hasPrefix(), 
                params.nested().isEndingSuffix()
        );
        
        return params.isByDefault()
                ? getWrappedRaw(getWrappedRawDefaultArgs.get())
                : getWrappedRaw(params.nested());                      
    }
    
    
    private static String getWrappedRaw(GetWrappedRawParams params) {
        return wrapping(params.wrappingRaw(), supplementFormatter(
                params.formatter(), params.hasPrefix(), params.isEndingSuffix()));
    }
    
    
    private static String supplementFormatter(String formatter, boolean hasPrefix, boolean isEndingSuffix) {
        return selectPrefix(hasPrefix) + formatter + selectSuffix(isEndingSuffix);
    }
    
    
    private static String selectPrefix(boolean hasPrefix) {
        return hasPrefix ? PREFIX : "";
    }
    
    
    private static String selectSuffix(boolean isEnding) {
        return isEnding ? ENDING_SUFFIX : DEFAULT_SUFFIX;
    }
    
        
    private static String wrapping(String str, String formatter) {
        return formatter.formatted(str);
    }
    
    
    private static String map(int number) {
        return map(number, NAMES);
    }
    
    
    private static String map(int number, String[] names) {
        var builder = new StringBuilder();
        
        for (int i = number, j = 0; i >= 1; i /= 2, j++) {
            var current = i % 2;
            var str = (1 == current) ? names[j] : "";
            
            builder.append((str.isEmpty() || 1 == i) ? str : (str + ":"));
        }
        
        return builder.toString();
    }
}
