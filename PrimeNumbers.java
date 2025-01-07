import static java.lang.Long.parseLong;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;


public class PrimeNumbers {
    private static final long LIMIT = 23 * 7;

 
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args.length == 1) {
                printPrimeNumbers(parseLong(args[0]));
            } else {
                printPrimeNumbers(parseLong(args[0]), parseLong(args[1]));
            }
        } else {
            printPrimeNumbers(LIMIT);
        }     
    }
    
    
    public static void printPrimeNumbers(final long start, final long limit) {
        if (start < 2) {
            throw new IllegalArgumentException("Начальное значение должно быть больше 2!");
        }
        
        if (limit < 0) {
            throw new IllegalArgumentException("Лимит не должен быть отрицательным!");
        }
        
        LongPredicate isCheckedNumber = number -> LongStream.iterate(3, i -> i < (long) Math.sqrt(number) + 1, i -> i + 2)
                .parallel()
                .noneMatch(i -> number % i == 0);
                                 
        var isPrimeNumber = ((LongPredicate) number -> number == 2)
                .or(((LongPredicate) number -> number % 2 != 0).and(isCheckedNumber));
        
        LongStream.iterate(start, i -> i <= start + limit - 2, i -> i + (start % 2 == 0 ? 1 : 2))
                .filter(isPrimeNumber)
                .forEach(System.out::println);
    }
    
    
    public static void printPrimeNumbers(final long limit) {
        printPrimeNumbers(2, limit);
    }
}



