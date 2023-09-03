public class DivModTest
{
    public static void main(String[] args)
    {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        
        System.out.println(x + " div " + y + " = " + (x / y));
        System.out.println(x + " mod " + y + " = " + (x % y));
    }
}