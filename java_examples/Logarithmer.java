public class Logarithmer
{
    public static void main(String[] args)
    {
        int number = Integer.parseInt(args[0]);
        
        System.out.println(log2(number));     
    }
    
    
    public static int log2(int n)
    {
        final int BASE = 2;
        int result = 0;
        int buffer = 0;
        
        for (; ; ++result)
        {
            buffer = (int) Math.pow(BASE, result);
            if (buffer == n)
            {
                break;
            }
        }
        
        return result;
    }
}