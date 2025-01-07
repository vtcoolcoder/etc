public class Recursion
{

    private static int counter = 0;
    
    private static String doRecursion(String r)
    {   
        System.out.println(counter++);
        
        if (counter == 10) 
        {
            return "|"; 
        }
        
        else
        {
            return doRecursion(r+r);
        }  
    }
    
    public static void main(String[] args)
    {
        System.out.println(doRecursion("*"));
    }
}