import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;


public class SwitchTester {
    private static final String PATTERN = "abcdefghijklmnopqrstvuwxyz";
    
    private static final Set<Character> DIGITS = initSetFromString("0123456789");      
    private static final Set<Character> LOWER_LETTERS = initSetFromString(PATTERN); 
    private static final Set<Character> UPPER_LETTERS = initSetFromString(PATTERN.toUpperCase());
    
    
    public static void main(String[] args) {
        showCollectionElements(LOWER_LETTERS);
        showCollectionElements(UPPER_LETTERS);
        showCollectionElements(DIGITS);
        
        System.out.println();
        
        generateLetters('a', 'z');
        generateLetters('A', 'Z');
        generateLetters('0', '9');
    }
    
    
    private static Set<Character> initSetFromString(String src) {
        return new HashSet<>(Arrays.asList(convertCharArrayToShell(src.toCharArray())));
    }
    
    
    private static Character[] convertCharArrayToShell(char[] src) {
        Character[] result = new Character[src.length];    
        //Character[] result = Arrays.copyOf(src, src.length); 
        for (int i = 0; i < src.length; i++) { result[i] = src[i]; }
        return result;
    }
    
    
    private static void showCollectionElements(Collection<?> collection) {
        collection.stream().forEach(System.out::print);
        System.out.println();
    }
    
    
    private static void generateLetters(char start, char stop) {
        for (char symbol = start; symbol <= stop; symbol++) { testSwitch(symbol); }
        System.out.println();
    }
    
    
    private static void testSwitch(char symbol) {
        final String MSG_ABT_LOW_LET = "The symbol \"" + symbol + "\" is a lower case letter.";
        final String MSG_ABT_UP_LET = "The symbol \"" + symbol + "\" is a upper case letter.";
        final String MSG_ABT_ANOTHER = "The symbol \"" + symbol + "\" is'nt a letter.";
        
        if (LOWER_LETTERS.contains(symbol)) { System.out.println(MSG_ABT_LOW_LET); }
        else if (UPPER_LETTERS.contains(symbol)) { System.out.println(MSG_ABT_UP_LET); }
        else { System.out.println(MSG_ABT_ANOTHER); }
    }
}