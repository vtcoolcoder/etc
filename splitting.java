// This is a snippet
long magic = 1000000009L;

public class PrimeNumbers {
    private static final long MIN_PARALLEL = 1_000_000L;
    
    private final Map<Range, Integer> THREADS_AMOUNT_MAP = new LinkedHashMap<>();  
    private final int THREADS_AMOUNT;
    private final ExecutorService EXECUTOR; 
    private final List<Future<Boolean>> FUTURES;

    private boolean isPrimeNumber = true;
    private long number;
    
    {
        THREADS_AMOUNT_MAP.put(new Range(0, MIN_PARALLEL - 1), 0);
        
        int i = 2;
        for (long n = MIN_PARALLEL; n > 0 && n < Long.MAX_VALUE && (n*2 - 1) > 0; n *= 2, i++) {
            THREADS_AMOUNT_MAP.put(new Range(n, n*2 - 1), i);
        }
    }
    
    
    public PrimeNumbers(long number) { 
        this.number = number; 
        
        THREADS_AMOUNT = getThreadAmount();
        THREADS_AMOUNT_MAP.clear();
        
        if (THREADS_AMOUNT > 0) {          
            EXECUTOR = Executors.newFixedThreadPool(THREADS_AMOUNT);          
            FUTURES = getFutures();
        } else {
            EXECUTOR = null; 
            FUTURES = null;
            isPrimeNumber = isPrimeNumber(2, number);     
        }
    }
    
    
    private record Range(long min, long max) {}
    
      
    private int getThreadAmount() {       
        var predicate = 
                ((Predicate<Map.Entry<Range, Integer>>) e -> number >= e.getKey().min())
                .and(e -> number <= e.getKey().max());  
        
        return THREADS_AMOUNT_MAP.entrySet().stream()
                .filter(predicate)
                .map(Map.Entry::getValue)
                .findFirst().get();
    }
    
    
    public boolean getResult() { 
        if (THREADS_AMOUNT <= 0) {
            return isPrimeNumber;
        } else {                   
            try {
                while (!hasAllResults(Future::isDone)) {
                    for (var target : FUTURES) {
                        if (target.isDone()) {
                            if (!target.get()) {
                                EXECUTOR.shutdownNow();
                                return false;
                            }
                        }
                    }
                }
                     
                EXECUTOR.shutdownNow();     
                return hasAllResults(e -> {
                            try {
                                return e.get();
                            } catch (Exception exc) {
                                throw new RuntimeException(exc);
                            }
                        });
                    
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }          
        }       
    }
    
    
    private List<Future<Boolean>> getFutures() {
        final List<Future<Boolean>> RESULT = new ArrayList<>();
        final long COEFFICIENT = number / THREADS_AMOUNT;
        
        for (int i = 1; i <= THREADS_AMOUNT; i++) {
            RESULT.add(getFutureByRange(new Range(
                    i == 1 ? 2 : COEFFICIENT*(i - 1) + 1, 
                    i != THREADS_AMOUNT ? COEFFICIENT*i : number
            )));
        }
        
        return RESULT;
    }
   
     
    private boolean isPrimeNumber(long min, long max) {
        if (number % 2 == 0) {
            return false;
        }
        
        for (
                long i = min; 
                max != number ? i <= max : i < max; 
                i += i % 2 == 0 ? 1 : 2
        ) {
            if (number % i == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    
    private Future<Boolean> getFutureByRange(Range range) {
        return EXECUTOR.submit(() -> isPrimeNumber(range.min(), range.max()));
    }
    
    
    private boolean hasAllResults(Predicate<Future<Boolean>> func) {
        boolean[] predicateParts = new boolean[FUTURES.size()];
        int idx = 0;
            
        for (var target : FUTURES) {
            predicateParts[idx++] = func.test(target);
        }
          
        return makePredicate(predicateParts);
    }
    
    
    private boolean makePredicate(boolean[] targets) {
        boolean result = true;
        
        for (var target : targets) {
            result &= target;
        }
        
        return result;
    }
}


class Primes {
    public static void printPrimeNumbers(final long start, final long limit) {
        for (long i = start; i <= limit + start - 2; i++) {
            if (new PrimeNumbers(i).getResult()) {
                System.out.printf("%d%n", i);
            }
        }
    }
    
    public static void printPrimeNumbers(long limit) {
        printPrimeNumbers(2, limit);
    }
}



