public class ArrayReverser
{
    private static int[] NUMBERS = 
        {101, 212, 323, 434, 545, 656, 767, 878, 989};
        
    private static int N = NUMBERS.length;
   
    
    public static void main(String[] args)
    {
        showServiceMessage("Before: ", NUMBERS);            
        reverseArray(NUMBERS); 
        showServiceMessage("After: ", NUMBERS);
    }
    
    
    private static void showServiceMessage(String msg, final int[] numbers)
    {
        System.err.println(msg);
        
        for (int number : numbers)
        {
            System.out.printf("%d\t", number);
        }
        
        System.out.println("\n");
    }
    
    
    private static void reverseArray(int[] source)
    {
        int N = source.length;
        
        for (int i = 0; i < N / 2; i++)
        {
            int cached = source[i];
            source[i] = source[N - 1 - i];
            source[N - 1 - i] = cached;
        }
    }
}