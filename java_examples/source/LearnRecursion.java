import java.util.HashMap;


public class LearnRecursion
{
    private static final String ILLEGAL = "Параметр должен быть не отрицательным!";
    private static final String CALLED = "Количество рекурсивных вызовов: ";
    private static final String CYCLE = "Циклом: ";
    private static final String RECURSION = "Рекурсией "; 
    private static final String WITH_DYNAMIC = 
        RECURSION + "с динамическим программированием: "; 
    private static final String WITHOUT_DYNAMIC = 
        RECURSION + "без динамического программирования: ";
    private static final String PREFIX = "fib(";
    private static final String SUFFIX = ") -> ";
    private static final String SOMETHING_PREFIX = "something(";
    private static final String SOMETHING_SUFFIX = SUFFIX;
    private static final String ERROR_MESSAGE = 
        "Задайте 1 целочисленный параметр больше нуля!";
    
    private static final int ONLY_ONE_ARG = 0;
    private static final int MIN_VALID_ARG_VALUE = 1;
    private static final int ARGS_LIMIT = 1;
    private static final int FAIL_EXIT_CODE = 1;
    
    private static long dynamicCounter=0L;
    private static long counter=0L;
    private static long somethingCounter=0L;
    private static int number;
    
    /*
        Как я понял концепцию динамического программирования:
        
        При первом вызове рекурсивной функции (если это рекурсивный случай)
        кэшировать в поле класса в виде отображения входной аргумент функции 
        -- на её возвращаемое значение:
        если пара аргумент=значение уже закэширована, 
        тогда извлечь из отображения по ключу (аргумент функции)
        -- значение (возвращаемое из функции по данному аргументу),
        чтобы сразу вернуть его из функции,
        -- во избежание лишних рекурсивных вызовов.
    */
    
    private static HashMap<Integer, Long> cache = new HashMap<>();
    
    
    public static void main(String[] args)
    {
        if (args.length != ARGS_LIMIT)
        {
            showErrorMessage();
        }
        try
        {
            number = Integer.parseInt(args[ONLY_ONE_ARG]);
        }
        catch (NumberFormatException ex)
        {
            showErrorMessage();
        }
        if (number < MIN_VALID_ARG_VALUE)
        {
            showErrorMessage();
        }
        
        System.out.println(PREFIX + number + SUFFIX);
        System.out.println(CYCLE + runCycleFib(number));
        System.out.println();
        
        System.out.println(WITH_DYNAMIC + runRecursiveDynamicFib(number));
        System.out.println(CALLED + dynamicCounter);
        System.out.println();
        
        System.out.println(WITHOUT_DYNAMIC + runRecursiveFib(number));
        System.out.println(CALLED + counter);
        System.out.println();
        
        System.out.println(SOMETHING_PREFIX + number + SOMETHING_SUFFIX);
        System.out.println(WITHOUT_DYNAMIC + runRecursiveSomething(number));
        System.out.println(CALLED + somethingCounter);
    }
    
    
    private static void showErrorMessage()
    {
        System.err.println(ERROR_MESSAGE);
        System.exit(FAIL_EXIT_CODE);
    }
    
    
    private static long runRecursiveSomething(int n)
    {
        ++somethingCounter;
        
        if (n < 2)
        {
            throw new IllegalArgumentException(ILLEGAL);
        }    
        else if (n == 2)
        {
            return 0;
        }
        else if(n == 3)
        {
            return 2;
        }
        
        return (2*n - 4) + runRecursiveSomething(n-1);
    }
    
    
    private static long runRecursiveFib(int n)
    {
        ++counter;
        
        if (n < 1)
        {
            throw new IllegalArgumentException(ILLEGAL);
        }
        else if (n == 1 || n == 2)
        {
            return 1L;
        }
        
        return runRecursiveFib(n-1) + runRecursiveFib(n-2);
    }
    
    
    private static long runRecursiveDynamicFib(int n)
    { 
        ++dynamicCounter;
        
        if (n < 1)
        {
            throw new IllegalArgumentException(ILLEGAL);
        }
        else if (n == 1 || n == 2)
        {
            return 1L;
        }
        else if (cache.containsKey(n))
        {
            return cache.get(n);
        }    
        
        long cacheR = runRecursiveDynamicFib(n-1);    
        long cacheL = runRecursiveDynamicFib(n-2);
        long result = cacheR + cacheL;
        cache.put(n, result);
        
        return result;
    }
    
    
    private static long runCycleFib(int n)
    { 
        if (n < 1)
        {
            throw new IllegalArgumentException(ILLEGAL);
        }
        else if (n == 1 || n == 2)
        {
            return 1L;
        }
      
        long[] buffer = new long[n]; 
        buffer[0] = buffer[1] = 1L;
        
        for (int i = 2; i < buffer.length; ++i)
        {
            buffer[i] = (long) (buffer[i-1]+buffer[i-2]);
        }
       
        return buffer[n-1];
    }
}