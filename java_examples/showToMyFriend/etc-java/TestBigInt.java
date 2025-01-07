import java.math.BigInteger;
import java.util.Map;
import java.util.LinkedHashMap;

public class TestBigInt {
    private static final int DEGREE = 23232; 
    
    public static void main(String[] args) {
        BigInteger firstBigInt = new BigInteger(String.valueOf(Long.MAX_VALUE));
        BigInteger secondBigInt = new BigInteger(String.valueOf(Long.MIN_VALUE));
    
        Map<String, BigInteger> numbers = new LinkedHashMap<>();
        
        numbers.put("Первое число", firstBigInt);
        numbers.put("Второе число", secondBigInt);
        numbers.put("Сумма их", firstBigInt.add(secondBigInt));  
        numbers.put("Разница их", firstBigInt.subtract(secondBigInt));
        numbers.put("Частное их", firstBigInt.divide(secondBigInt));
        numbers.put("Произведение их", firstBigInt.multiply(secondBigInt));
        numbers.put(DEGREE + " степень первого числа", firstBigInt.pow(DEGREE));
        numbers.put(DEGREE + " степень второго числа", secondBigInt.pow(DEGREE));  
        
        for (Map.Entry<String, BigInteger> entry : numbers.entrySet()) {
            System.out.printf("%s: %s%n%n", entry.getKey(), entry.getValue());
        } 
    }
}