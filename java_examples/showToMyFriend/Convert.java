import java.util.Map;
import java.util.HashMap;


public class Convert {    
    private enum Mode { 
        TOCYRILLIC("" + LOW_CHAR_LIM, "" + HIGH_CHAR_LIM) { 
            private final Map<Character, Byte> map = new HashMap<>();
            
            { fillMap(map, SubMode.CYRILLIC); }
            
            @Override
            public Map<Character, Byte> getMap() { return map; }
        }, 
      
        
        TOSOURCE("" + FIRST_ELEM_IDX, "" + LAST_ELEM_IDX) { 
            private final Map<Byte, Character> map = new HashMap<>(); 
            
            { fillMap(map, SubMode.SOURCE); }
            
            @Override
            public Map<Byte, Character> getMap() { return map; }
        }; 
        
        
        public abstract Map<?, ?> getMap();
        
        
        Mode(final String low, final String high) {
            lowLimit = low;
            highLimit = high;
        }
         
           
        public String getExceptionMessage() {       
            final String START_PART = "Разряд должен быть в диапазоне от ";
            final String MIDDLE_PART = " до ";
            final String END_PART = " включительно!";
            return START_PART + lowLimit + MIDDLE_PART + highLimit + END_PART;   
        }
        
        
        private static void fillMap(final Map<?, ?> map, final SubMode mode) {
            for (int i = 0; i < LETTERS.length; i++) { 
                byte number = (byte) i;
                char letter = LETTERS[i];
                
                switch (mode) {
                    case CYRILLIC:
                        ((Map<Character, Byte>) map).put(letter, number);
                        break;
                    case SOURCE:
                        ((Map<Byte, Character>) map).put(number, letter);
                        break;
                }
            }
        }
        
        
        private enum SubMode { CYRILLIC, SOURCE }
        private String lowLimit, highLimit;
    }
    
    
    private static final char[] LETTERS = 
        "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toUpperCase().toCharArray();
    private static final int FIRST_ELEM_IDX = 0;
    private static final int LAST_ELEM_IDX = LETTERS.length - 1;
    private static final char LOW_CHAR_LIM = LETTERS[FIRST_ELEM_IDX];
    private static final char HIGH_CHAR_LIM = LETTERS[LAST_ELEM_IDX];
    private static final int BASE = 33;
    private static final int FIRST_MAIN_ARG = 0;
    private static final String ARROW = " ===>> ";

    private static long size = 1000;
    
    
    public static void main(String[] args) {
        validate(args);
        showContent();      
    }
    
    
    public static String convertToCyrillic(long n) {
        StringBuilder buffer = new StringBuilder();
        
        while (n != 0) {       
            buffer.append("" + getGenDigit((byte) (n % BASE), Mode.TOSOURCE));   
            n /= BASE;    
        } 
        
        return new String(buffer.reverse());
    }
    
         
    public static long convertCyrillicToSource(final String converted) {
        final char[] buffer = converted.toCharArray();
        final int bufferLastIndex = buffer.length - 1;
        
        long result = 0;
        
        for (int i = bufferLastIndex, currentDegree = 0; i >= 0; i--, currentDegree++) {
            byte currentDigit = getGenDigit(buffer[i], Mode.TOCYRILLIC); 
            result += currentDigit * Math.pow(BASE, currentDegree);    
        }
        
        return result;
    }
    
    
    private static void validate(final String[] args) {
        if (args.length != 0) { 
            try {
                size = Math.abs(Long.parseLong(args[FIRST_MAIN_ARG]));
            } catch (NumberFormatException ex) {
                System.err.println("Некорректное значение аргумента командной строки!");
                System.err.println("Значение должно быть числом.");
                System.err.println("Используется значение по умолчанию: " + size);
            }
        }
    }
    
    
    private static void showContent() {
        for (long i = 0; i <= size; i++) {
            System.out.print("Исходное значение: " + i + ARROW);
            
            String cyrillicNumber = convertToCyrillic(i);
            
            System.out.println(cyrillicNumber);
            System.out.print("Обратное преобразование: " + cyrillicNumber + ARROW);
            
            long inversionValue = convertCyrillicToSource(cyrillicNumber);
            
            System.out.println(inversionValue);
            System.out.print("Исходное значение равно обратно преобразованному? -- ");
            System.out.println(i == inversionValue);
            System.out.println();
        }
    }
    
        
    private static <K, V> V getGenDigit(final K digit, final Mode mode) {
        final String ERR_MSG = mode.getExceptionMessage();
        Map<K, V> map = (Map<K, V>) mode.getMap();
        if (! map.containsKey(digit)) { throw new IllegalArgumentException(ERR_MSG); }
        return map.get(digit);
    } 
}