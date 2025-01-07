import java.util.Objects;


public final class JavaDocBashScriptHelper {

    private static final String FORMATTER = 
            "    javadoc -d docs -sourcepath \"$i\" -subpackages %s || \\";
            
    public static final String[] NAMES = {
            "java", // 0 java
            "javax", // 1 javax
            "org", // 2 org
            "com", // 3 com
            "sun", // 4 sun
            "netscape", // 5 netscape
            "jdk" // 6 jdk
    };
    
    private final String[] names;
    
    public JavaDocBashScriptHelper(String[] names) {
        Objects.requireNonNull(names);
        this.names = names;
    }
    
    public void generate() {
        generate((int) Math.pow(2, names.length), false);
    }
    
    public static void generateByDefault() {
        generate((int) Math.pow(2, NAMES.length));
    }
    
    private static void generate(int bound) {
        generate(bound, true);
    }
    
    private static void generate(int bound, boolean isByDefault) {
        System.out.println("generate() {");
        
        for (int i = 1; i < bound; i++) {
            System.out.println(wrapping(isByDefault ? map(i) : map(i, numbers)));
        }
        
        System.out.println("}");
    }
    
    private static String wrapping(String str) {
        return FORMATTER.formatted(str);
    }
    
    private static String map(int number) {
        return map(number, NAMES);
    }
    
    private static String map(int number, String[] names) {
        var builder = new StringBuilder();
        
        for (int i = number, j = 0; i >= 1; i /= 2, j++) {
            var current = i % 2;
            var str = current == 1 ? names[j] : "";
            builder.append(str.isEmpty() || 1 == i
                    ? str 
                    : str + ":");
        }
        
        return builder.toString();
    }
}


