import java.util.*;


public class EratospheneSieve
{
    private static final int N = 6969;
    private static boolean[] isPrime = new boolean[N+1];       
    
      
    public static void main(String[] args)
    {            
        sieveNumbers(); 
        showPrimeNumbers();                
    }
    
    
    private static void sieveNumbers()
    {
        Arrays.fill(isPrime, true);
        
        for (int x = 2; x*x <= N; x++)
        {
            if (isPrime[x])
            {
                for (int y = x*x; y <= N; y += x)
                {
                    isPrime[y] = false;
                }
            }
        }
    }
    
    
    private static void showPrimeNumbers()
    {
        int primeNumberCounter = 0;   
            
        for (int x = 2; x <= N; x++)
        {
            if (isPrime[x])
            {
                primeNumberCounter++;
                
                System.out.printf("%d\t", x);
                
                if (primeNumberCounter % 10 == 0)
                {
                    System.out.println();
                }
            }      
        }   
    }
}