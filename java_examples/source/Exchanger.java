public class Exchanger
{
    private static int x, y;
    
    public static void main(String[] args)
    {
        x = Integer.parseInt(args[0]);
        y = Integer.parseInt(args[1]);
        
        System.out.println(x + "\t" + y);
        
        x ^= y;
        y ^= x;
        x ^= y;
        
        System.out.println(x + "\t" + y);
        System.out.println();
    }
}