import java.util.ArrayList;


public class Fibonacci
{
    private static final String ERROR_MESSAGE =
        "Задайте 1 целочисленный аргумент командной строки!";
    private static final String ILLEGAL_ARGUMENT =
        "Значение n должно быть в диапазоне от 1 до 92 включительно!";
    private static final String UNKNOWN_ERROR = "Неизвестная ошибка!";
        
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 92;
    
    private static final int FAIL = 1;
    private static final int VALID_MAIN_ARGS_SIZE = 1;
    private static final int FIRST_MAIN_ARG = 0;
        
    private static final long INIT_VALUE = 1;
    private static final int INIT_START_INDEX = 0;
    private static final int INIT_VALUES_LIMIT = 2;
    
    private static final int FIRST_PREV_LAST_NUMB_OFFSET = 1;
    private static final int SECOND_PREV_LAST_NUMB_OFFSET = 2;
    
           
    ArrayList<Long> result = new ArrayList<Long>();
    final int n;
  
    
    public static void main(String[] args)
    { 
        if (isStartingFail(args.length))
        {
            showErrorMessage(ERROR_MESSAGE);
        }
        
        try
        {   
            showResult(args);                              
        }
        catch (NumberFormatException ex)
        {
            showErrorMessage(ERROR_MESSAGE);
        }
        catch (IllegalArgumentException ex)
        {
            showErrorMessage(ILLEGAL_ARGUMENT);
        } 
        catch (Exception ex)
        {
            showErrorMessage(UNKNOWN_ERROR);
        }               
    }
    
    
    public Fibonacci(int n)
    {
        this.n = checkN(n);
        runFibonacci();
    }
    
    
    public ArrayList<Long> getFullItemsList()
    {
        return result;
    }
    
    
    public long getLastNumber()
    {
        int lastNumberIndex = result.size() - FIRST_PREV_LAST_NUMB_OFFSET;
        return result.get(lastNumberIndex); 
    }
    
    
    private int checkN(int n)
    {
        if (isValidNRange(n))
        {
            return n;
        }
        else
        {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
    }
    
    
    private boolean isValidNRange(int n)
    {
        return ((n >= MIN_VALUE) && (n <= MAX_VALUE));
    }
    
        
    private void runFibonacci()
    {        
        setInittingValues();      
        setFactValues();
    }
    
    
    private void setInittingValues()
    {
        for (int i = INIT_START_INDEX; i < INIT_VALUES_LIMIT; ++i)
        {
            result.add(INIT_VALUE);         
        }
    }
    
    
    private void setFactValues()
    {
        for (int i = INIT_VALUES_LIMIT; i < n; ++i)
        {
            int firstPrevNumbIndex = i - FIRST_PREV_LAST_NUMB_OFFSET;
            long firstPrevNumb = result.get(firstPrevNumbIndex);
            
            int secondPrevNumbIndex = i - SECOND_PREV_LAST_NUMB_OFFSET;
            long secondPrevNumb = result.get(secondPrevNumbIndex);
            
            result.add(firstPrevNumb + secondPrevNumb);  
        }     
    }
    
    
    private static boolean isStartingFail(int mainArgsSize)
    {
        return (mainArgsSize != VALID_MAIN_ARGS_SIZE);
    }
    
    
    private static void showErrorMessage(String msg)
    {
        System.err.println(msg);
        System.exit(FAIL);
    }  
    
    
    private static void showResult(String[] args)
    {
        int fibonacciN = Integer.parseInt(args[FIRST_MAIN_ARG]);                
        Fibonacci aFibonacci = new Fibonacci(fibonacciN);  
            
        System.err.println(aFibonacci.getFullItemsList());   
        System.out.println(aFibonacci.getLastNumber()); 
    }
}