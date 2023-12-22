public class Convert {
    private enum Mode { 
        TOCYRILLIC("а", "я"), 
        TOSOURCE("0", "32"); 
        
        Mode(String low, String high) {
            lowLimit = low;
            highLimit = high;
        }
        
        public String getExceptionMessage() {       
            final String startPart = "Разряд должен быть в диапазоне от ";
            final String middlePart = " до ";
            final String endPart = " включительно!";
            return startPart + lowLimit + middlePart + highLimit + endPart;   
        }
        
        private String lowLimit, highLimit;
    }
    
    
    private static final int BASE = 33;
    private static final int FIRST_MAIN_ARG = 0;
    private static final String ARROW = " ===>> ";

    private static long size = 1000;
    
    public static void main(String[] args) {
        validate(args);
        showContent();      
    }
    
    
    private static void validate(String[] args) {
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
    
    
    private static String convertToCyrillic(long n) {
        StringBuilder buffer = new StringBuilder();
        
        while (n != 0) {       
            buffer.append("" + getConvertedDigit((byte) (n % BASE)));   
            n /= BASE;    
        } 
        
        return new String(buffer.reverse());
    }
    
        
    private static char getConvertedDigit(final byte digit) {
        final String ERR_MSG = Mode.TOSOURCE.getExceptionMessage();
        return switch (digit) {
            case 0 -> 'а';
            case 1 -> 'б';
            case 2 -> 'в';
            case 3 -> 'г';
            case 4 -> 'д';
            case 5 -> 'е';
            case 6 -> 'ё';
            case 7 -> 'ж';
            case 8 -> 'з';
            case 9 -> 'и';
            case 10 -> 'й';
            case 11 -> 'к';
            case 12 -> 'л';
            case 13 -> 'м';
            case 14 -> 'н';
            case 15 -> 'о';
            case 16 -> 'п';
            case 17 -> 'р';
            case 18 -> 'с';
            case 19 -> 'т';
            case 20 -> 'у';
            case 21 -> 'ф';
            case 22 -> 'х';
            case 23 -> 'ц';
            case 24 -> 'ч';
            case 25 -> 'ш';
            case 26 -> 'щ';
            case 27 -> 'ъ';
            case 28 -> 'ы';
            case 29 -> 'ь';
            case 30 -> 'э';
            case 31 -> 'ю';
            case 32 -> 'я';
            default -> throw new IllegalArgumentException(ERR_MSG);
        };
    }
    
    
    private static long convertCyrillicToSource(final String converted) {
        final char[] buffer = converted.toCharArray();
        final int bufferLastIndex = buffer.length - 1;
        
        long result = 0;
        
        for (int i = bufferLastIndex, currentDegree = 0; i >= 0; i--, currentDegree++) {
            byte currentDigit = getSourceDigit(buffer[i]); 
            result += currentDigit * Math.pow(BASE, currentDegree);    
        }
        
        return result;
    }
    
    
    private static byte getSourceDigit(final char digit) {
        final String ERR_MSG = Mode.TOCYRILLIC.getExceptionMessage();
        return switch (digit) {
            case 'а' -> 0;
            case 'б' -> 1;
            case 'в' -> 2;
            case 'г' -> 3;
            case 'д' -> 4;
            case 'е' -> 5;
            case 'ё' -> 6;
            case 'ж' -> 7;
            case 'з' -> 8;
            case 'и' -> 9;
            case 'й' -> 10;
            case 'к' -> 11;
            case 'л' -> 12;
            case 'м' -> 13;
            case 'н' -> 14;
            case 'о' -> 15;
            case 'п' -> 16;
            case 'р' -> 17;
            case 'с' -> 18;
            case 'т' -> 19;
            case 'у' -> 20;
            case 'ф' -> 21;
            case 'х' -> 22;
            case 'ц' -> 23;
            case 'ч' -> 24;
            case 'ш' -> 25;
            case 'щ' -> 26;
            case 'ъ' -> 27;
            case 'ы' -> 28;
            case 'ь' -> 29;
            case 'э' -> 30;
            case 'ю' -> 31;
            case 'я' -> 32;
            default -> throw new IllegalArgumentException(ERR_MSG);
        };
    }  
}