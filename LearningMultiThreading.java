import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.util.function.*;


public class LearningMultiThreading {

    @FunctionalInterface
    private interface FunctionWithException<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    private record Student(String name, String surname, int age) {}

    private static final int LIMIT = 7;
    private static final int TIMEOUT = 2323;
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(LIMIT);
    private static final int[] DIGITS = IntStream.range(0, 10).toArray();
    private static final Student STUDENT = new Student("Риолина", "Иванова", 23);
    

    public static void main(String[] args) throws Exception {     
        var future = getFuture("первый", 23);   
        var secondFuture = getFuture("второй", "23:23");     
        var thirdFuture = getFuture("третий", 23.23);   
        var fourthFuture = getFuture("четвёртый", 'G');   
        var fifthFuture = getFuture("пятый", true);      
        var sixthFuture = getFuture("шестой", DIGITS);   
        var seventhFuture = getFuture("седьмой", STUDENT);   
        
        var futures = List.of(
                future, 
                secondFuture, 
                thirdFuture, 
                fourthFuture, 
                fifthFuture,
                sixthFuture, 
                seventhFuture                  
        );
        
        EXECUTOR.shutdown();
        
        futures.stream()
                .map(wrapping(Future::get))
                .map(LearningMultiThreading::getStringFromArrayOrIdentity)
                .forEach(System.out::println);    
                
        EXECUTOR.awaitTermination(1, TimeUnit.DAYS);
    }
    
    
    private static <T> Callable<T> getCallable(String threadName, T returning) {
        Consumer<String> printing = 
                state -> System.out.printf("%s поток %s...%n", state, threadName);
                
        return () -> {
            printing.accept("запущен");
            Thread.sleep(TIMEOUT);
            printing.accept("завершён");
            return returning;
        };
    }
    
    
    private static <T> Future<T> getFuture(String threadName, T returning) {
        return EXECUTOR.submit(getCallable(threadName, returning));
    }
    
    
    private static <T, R, E extends Exception> 
    Function<T, R> wrapping(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    
    private static Object getStringFromArrayOrIdentity(Object obj) {
        return obj instanceof long[] longArray 
            ? Arrays.toString(longArray)
            : obj instanceof int[] intArray 
                ? Arrays.toString(intArray)
                : obj instanceof short[] shortArray
                    ? Arrays.toString(shortArray)
                    : obj instanceof char[] charArray
                        ? Arrays.toString(charArray)
                        : obj instanceof byte[] byteArray
                            ? Arrays.toString(byteArray)
                            : obj instanceof boolean[] boolArray
                                ? Arrays.toString(boolArray)
                                : obj instanceof float[] floatArray
                                    ? Arrays.toString(floatArray)
                                    : obj instanceof double[] doubleArray
                                        ? Arrays.toString(doubleArray)
                                        : obj;
    }
}