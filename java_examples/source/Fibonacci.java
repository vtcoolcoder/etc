import java.util.ArrayList;


public class Fibonacci
{
    private static final String ERROR_MESSAGE =
        "Задайте 1 целочисленный аргумент командной строки!";
    private static final String ILLEGAL_ARGUMENT =
        "Значение n должно быть в диапазоне от 1 до 92 включительно!";
         
    ArrayList<Long> result = new ArrayList<Long>();
    int n;
  
    
    public static void main(String[] args)
    { 
        if (args.length != 1)
        {
            showErrorMessage(ERROR_MESSAGE);
        }
        
        try
        {                   
            Fibonacci aFibonacci = new Fibonacci(Integer.parseInt(args[0]));  
            System.err.println(aFibonacci.getFullItemsList());   
            System.out.println(aFibonacci.getLastNumber());                        
        }
        catch (NumberFormatException ex)
        {
            showErrorMessage(ERROR_MESSAGE);
        }
        catch (IllegalArgumentException ex)
        {
            showErrorMessage(ILLEGAL_ARGUMENT);
        }                
    }
    
    
    public Fibonacci(int n)
    {
        setN(n);
        runFibonacci();
    }
    
    
    public ArrayList<Long> getFullItemsList()
    {
        return result;
    }
    
    
    public long getLastNumber()
    {
        return result.get(result.size() - 1); 
    }
    
    
    private void setN(int n)
    {
        if (n < 1 || n > 92)
        {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
        else
        {
            this.n = n;
        }
    }
    
        
    private void runFibonacci()
    {        
        setInittingValues();      
        setFactValues();
    }
    
    
    private void setInittingValues()
    {
        for (int i = 0; i < 2; ++i)
        {
            result.add((long)1);         
        }
    }
    
    
    private void setFactValues()
    {
        for (int i = 2; i < n; ++i)
        {
            result.add(result.get(i - 1) + result.get(i - 2));  
        }     
    }
    
    
    private static void showErrorMessage(String msg)
    {
        System.err.println(msg);
        System.exit(1);
    }  
}