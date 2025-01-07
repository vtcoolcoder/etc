public class NumberToCyrillicConverter {
    private static final String ERROR_MESSAGE = "Обнаружен некорректный аргумент: ";
    public static void main(String[] args) {
        for (String string : args) {
            try {
                long currentNumber = Long.parseLong(string);
                String cyrillic = NumberConverter.convertToCyrillic(currentNumber);
                System.out.println(cyrillic);
            } catch (NumberFormatException ex) {
                System.err.println(ERROR_MESSAGE + string);
            }
        }  
    }
}