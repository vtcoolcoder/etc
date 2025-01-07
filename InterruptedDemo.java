import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.OptionalInt;

import java.util.concurrent.*;


public class InterruptedDemo {
    private static final long LIMIT = (long) Integer.MAX_VALUE - (long) Integer.MIN_VALUE;
    private static final int TASK_AMOUNT = 8;
    private static final int STEP = (int) (LIMIT / TASK_AMOUNT);  
    private static final List<Future<OptionalInt>> TASKS = new ArrayList<>();
    private static final int MAGIC_NUMBER = new Random().nextInt();
    
    private static volatile boolean isContinueBruteForce = true;
    
    
    public static void main(String[] args) throws Exception {
        System.out.println("Загаданное число: " + MAGIC_NUMBER);
        
        var executorService = Executors.newFixedThreadPool(TASK_AMOUNT);  
        
        for (int i = 0; i < TASK_AMOUNT; i++) {
            int start = STEP * i + (i + Integer.MIN_VALUE);
                                    
            int end = i == TASK_AMOUNT - 1
                    ? Integer.MAX_VALUE
                    : start + STEP;
            
            TASKS.add(executorService.submit(() -> findNumber(start, end))); 
        }
        
        executorService.shutdown();
        
        System.out.println("Найденное число: " + getFindedNumber());
    }
    
    
    private static OptionalInt findNumber(final int start, final int end) {                 
        for (int i = start; i <= end; i++) {
            if (! isContinueBruteForce) {
                return OptionalInt.empty();
            } else if (MAGIC_NUMBER == i) {
                isContinueBruteForce = false;
                return OptionalInt.of(i);
            }   
        }
        
        return OptionalInt.empty();
    }
    
    
    private static int getFindedNumber() {
        return TASKS.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } 
                }).filter(OptionalInt::isPresent)
                .mapToInt(OptionalInt::getAsInt)
                .findAny().getAsInt();
    }
}