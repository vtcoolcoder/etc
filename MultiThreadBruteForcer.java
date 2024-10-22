import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.OptionalLong;
import java.math.BigInteger;
import java.util.function.Function;

import java.util.concurrent.*;


public class MultiThreadBruteForcer {

    private static final BigInteger LIMIT =  
            BigInteger.valueOf(Long.MAX_VALUE).subtract(BigInteger.valueOf(Long.MIN_VALUE));          
    private static final int TASK_AMOUNT = Runtime.getRuntime().availableProcessors();  
    private static final long STEP = LIMIT.divide(BigInteger.valueOf(TASK_AMOUNT)).longValue();  
    private static final List<Future<OptionalLong>> TASKS = new ArrayList<>();
    private static final long MAGIC_NUMBER = new Random().nextLong();
    
    private static volatile boolean isContinueBruteForce = true;
    private static long guessedNumber = MAGIC_NUMBER; 
    
    
    public static void main(String[] args) throws Exception {       
        tryParseCLArg(args);
        System.out.println("Загаданное число: " + guessedNumber);
        setTasks();   
        System.out.println("Найденное число: " + getFoundNumber());
    }
    
    
    private static void tryParseCLArg(final String[] args) {
        if (args.length > 0) {
            try {
                guessedNumber = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {}      
        } 
    }
    
    
    private static void setTasks() {      
        for (int i = 0; i < TASK_AMOUNT; i++) {
            long start = STEP*i + (i + Long.MIN_VALUE);                       
            long end = (i == TASK_AMOUNT - 1) ? Long.MAX_VALUE : (start + STEP);        
            TASKS.add(CompletableFuture.supplyAsync(() -> findNumber(start, end)));
        }
    }
    
    
    private static OptionalLong findNumber(final long start, final long end) {     
        long medium = (end - start + 1) / 2;
        
        for (
                long i = start, 
                n = 0, 
                j = 0, 
                k = end; 
                        
                i <= end && k >= start; 
                
                i++, 
                j++, 
                k--, 
                n += (j % 2 != 0) ? 1 : 0
        ) {  
            if (! isContinueBruteForce) {
                return OptionalLong.empty();
            } else if (hasResult(i, k, j, n, start, medium)) {
                isContinueBruteForce = false;         
                return OptionalLong.of(getResult(i, k, getCurrentNumber(j, n, start, medium)));
            }   
        }
              
        return OptionalLong.empty();
    }
    
    
    private static long getCurrentNumber(long j, long n, long start, long medium) {
        long offset = (j % 2 == 0) ? n : -n;
        return start + medium + offset;
    }
    
    
    private static boolean hasResult(long i, long k, long j, long n, long start, long medium) {
        return guessedNumber == i 
                || guessedNumber == k
                || guessedNumber == getCurrentNumber(j, n, start, medium);
    }
    
    
    private static long getResult(long i, long k, long currentNumber) {
        return guessedNumber == i ? i : guessedNumber == k ? k : currentNumber;
    }
    
    
    private static long getFoundNumber() {
        return TASKS.stream()
                .map(tryCatchWrapping())
                .filter(OptionalLong::isPresent)
                .mapToLong(OptionalLong::getAsLong)
                .findAny().getAsLong();
    }
    
    
    private static Function<Future<OptionalLong>, OptionalLong> tryCatchWrapping() {
        return future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } 
        };
    }
}