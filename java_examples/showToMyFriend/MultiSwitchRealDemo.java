public class MultiSwitchRealDemo {
    public static void main(String[] args) {
        generateSymbols('0', '9');
        generateSymbols('a', 'z');
        generateSymbols('A', 'Z');
    }
    
    
    private static void generateSymbols(char start, char stop) {
        for (char c = start; c <= stop; c++) {
            System.out.println("Symbol \"" + c + "\" is " + getRegister(c));
        }
        
        System.out.println();
    }
    
    
    private static String getRegister(char item) {
        return switch (item) {
            case 'a','b','c','d','e','f','g','h','i','j','k','l','m',
                 'n','o','p','q','r','s','t','v','u','w','x','y','z' -> "LOWER_CASE";
                 
            case 'A','B','C','D','E','F','G','H','I','J','K','L','M',
                 'N','O','P','Q','R','S','T','V','U','W','X','Y','Z' -> "UPPER_CASE";
                 
            default -> "UNDEFINED";
        };
    }
}