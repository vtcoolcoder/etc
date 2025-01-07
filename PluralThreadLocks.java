import java.util.ArrayList;
import java.util.function.Consumer;


public class PluralThreadLocks {
    @FunctionalInterface
    private interface RunnableWithException<E extends Exception> {
        void run() throws E;
    }
    
    @FunctionalInterface
    private interface ConsumerWithException<T, E extends Exception> {
        void accept(T t) throws E;
    }
    
    
    private static final int THREADS_AMOUNT = 7;

    private static final Object LOCK = new Object();
    private static final Object SECOND_LOCK = new Object();
    private static final Object THIRD_LOCK = new Object();
    private static final Object FOURTH_LOCK = new Object();
    private static final Object FIFTH_LOCK = new Object();
    private static final Object SIXTH_LOCK = new Object();
    private static final Object SEVENTH_LOCK = new Object();
   
    
    public static void main(String[] args) throws Exception {
        var threads = new ArrayList<Thread>();
        
        for (int i = 0; i < THREADS_AMOUNT; i++) {
            threads.add(new Thread(PluralThreadLocks::runAll));
        }
        
        threads.forEach(Thread::start);
        threads.forEach(wrappingConsumer(Thread::join));
    }
    
    private static <T, E extends Exception> 
    Consumer<T> wrappingConsumer(final ConsumerWithException<T, E> action) {
        return item -> {
            try {
                action.accept(item);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    private static <E extends Exception> 
    Runnable wrappingRunnable(final RunnableWithException<E> action) {
        return () -> {
            try {
                action.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    private static void runAll() {
        runFirst(PluralThreadLocks::printCurrentThreadName);
        runSecond(PluralThreadLocks::printCurrentThreadName);
        runThird(PluralThreadLocks::printCurrentThreadName);
        runFourth(PluralThreadLocks::printCurrentThreadName);
        runFifth(PluralThreadLocks::printCurrentThreadName);
        runSixth(PluralThreadLocks::printCurrentThreadName);
        runSeventh(PluralThreadLocks::printCurrentThreadName);
    }
    
    private static void runFirst(final RunnableWithException action) { 
        runGenerally(LOCK, wrappingRunnable(action)); 
    }
        
    private static void runSecond(final RunnableWithException action) { 
        runGenerally(SECOND_LOCK, wrappingRunnable(action)); 
    }       
    
    private static void runThird(final RunnableWithException action) { 
        runGenerally(THIRD_LOCK, wrappingRunnable(action)); 
    }   
            
    private static void runFourth(final RunnableWithException action) { 
        runGenerally(FOURTH_LOCK, wrappingRunnable(action)); 
    }
    
    private static void runFifth(final RunnableWithException action) { 
        runGenerally(FIFTH_LOCK, wrappingRunnable(action)); 
    }
    
    private static void runSixth(final RunnableWithException action) { 
        runGenerally(SIXTH_LOCK, wrappingRunnable(action)); 
    }
    
    private static void runSeventh(final RunnableWithException action) { 
        runGenerally(SEVENTH_LOCK, wrappingRunnable(action)); 
    }
    
    private static void runGenerally(final Object lock, final Runnable action) {
        synchronized (lock) {
            action.run();
        }
    }
    
    private static void printCurrentThreadName() {
        System.out.println(Thread.currentThread().getName());
    }
}