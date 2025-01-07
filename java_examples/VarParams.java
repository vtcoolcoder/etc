public class VarParams
{
    private static int getSum(int ... summed)
    {
        int result = 0;
        
        for (int current : summed)
        {
            result += current;
        }
        
        return result;
    }
    
    public static void main(String[] args)
    {
        System.out.println(
            getSum(1, 3, 5, 7, 9) + "\n" +
            getSum(2, 4, 6, 8) + "\n" +
            getSum(-1, -2, -3) + "\n" +
            getSum(-5, 5) + "\n" +
            getSum(13) + "\n" +
            getSum()
        );
    }
}