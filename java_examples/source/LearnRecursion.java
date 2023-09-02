import java.util.HashMap;


public class LearnRecursion
{
    private static final String ILLEGAL = "Параметр должен быть не меньше 1!";
    private static final String CALLED = "Количество рекурсивных вызовов: ";
    private static final String CYCLE = "Циклом: ";
    private static final String RECURSION = "Рекурсией "; 
    private static final String WITH_DYNAMIC = 
        RECURSION + "с динамическим программированием: "; 
    private static final String WITHOUT_DYNAMIC = 
        RECURSION + "без динамического программирования: ";
    private static final String PREFIX = "fib(";
    private static final String SUFFIX = ") -> ";
    private static final String ERROR_MESSAGE = 
        "Задайте 1 целочисленный параметр больше нуля!";
    
    private static final int ONLY_ONE_ARG = 0;
    private static final int MIN_VALID_ARG_VALUE = 1;
    private static final int ARGS_LIMIT = 1;
    private static final int FAIL_EXIT_CODE = 1;
    
    private static long dynamicCounter=0L;
    private static long counter=0L;
    
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
        checkInputtingData(args);
        showResults();     
    }
    
    
    private static void checkInputtingData(String[] args)
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
    }
    
    
    private static void showErrorMessage()
    {
        System.err.println(ERROR_MESSAGE);
        System.exit(FAIL_EXIT_CODE);
    }
    
    
    private static void showResults()
    {   
        printCurrentResult(PREFIX + number + SUFFIX, 
                           CYCLE + runCycleFib(number));
                           
        printCurrentResult(WITH_DYNAMIC + runRecursiveDynamicFib(number),
                           CALLED + dynamicCounter);
                           
        printCurrentResult(WITHOUT_DYNAMIC + runRecursiveFib(number),
                           CALLED + counter);
    }
    
    
    private static void printCurrentResult(String first, String second)
    {
        System.out.println(first);
        System.out.println(second);
        System.out.println();
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
    
    /**
    * Это пример рекурсии с кэшированием (вариант динамического программирования)
    */
    private static long runRecursiveDynamicFib(int n)
    { 
        ++dynamicCounter;
        
        if (n < 1)
        {
            throw new IllegalArgumentException(ILLEGAL);
        }
        else if (cache.containsKey(n))
        {
            return cache.get(n);
        }   
        else if (n == 1 || n == 2)
        {
            return 1L;
        }
               
        long result = runRecursiveDynamicFib(n-1) + runRecursiveDynamicFib(n-2);
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