public class CyrillicToNumberConverter {
    public static void main(String[] args) {
        for (String string : args) {
            long currentNumber = 
                NumberConverter.convertCyrillicToSource(string.toUpperCase());
            System.out.println(currentNumber);
        }  
    }
}