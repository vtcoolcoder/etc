// This is a snippet
import static java.lang.Integer.parseInt;

public class PrimeNumbers {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args.length == 1) {
                printPrimeNumbers(parseInt(args[0]));
            } else {
                printPrimeNumbers(parseInt(args[0]), parseInt(args[1]));
            }
        }      
    }
    
    
    public static void printPrimeNumbers(final int start, final int limit) {
        if (start < 2) {
            throw new IllegalArgumentException("Начальное значение должно быть больше 2!");
        }
        
        if (limit < 0) {
            throw new IllegalArgumentException("Лимит не должен быть отрицательным!");
        }
        
        IntPredicate isCheckedNumber = number -> IntStream.iterate(3, i -> i < number, i -> i + 2)
                .parallel()
                .noneMatch(i -> number % i == 0);
                                 
        var isPrimeNumber = ((IntPredicate) number -> number == 2)
                .or(((IntPredicate) number -> number % 2 != 0).and(isCheckedNumber));
        
        IntStream.iterate(start, i -> i <= start + limit - 2, i -> i + (start % 2 == 0 ? 1 : 2))
                .filter(isPrimeNumber)
                .forEach(System.out::println);
    }
    
    
    public static void printPrimeNumbers(final int limit) {
        printPrimeNumbers(2, limit);
    }
}



