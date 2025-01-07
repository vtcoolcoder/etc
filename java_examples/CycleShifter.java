import java.util.*;


public class CycleShifter
{
    private interface Shifter
    {
        void shift(ArrayList<Integer> A);
    }

    private static final String LEFT = "<-";
    private static final String RIGHT = "->";
    private static final String SRC = "Source";
    private static final String SOURCE = SRC + ": ";
    private static final String SOURCE_LENGTH = SRC + " length: ";
    private static final String FORMAT = "%d %s %s%n";

    private static ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(
        101, 212, 323, 434, 545, 656, 767, 878, 989
    ));
        
    private static int numbersLen = numbers.size();


    public static void main(String[] args)
    {
        System.out.println(SOURCE + numbers); 
        System.out.println(SOURCE_LENGTH + numbersLen);
        System.out.println();      
        
        showFullShift(LEFT, CycleShifter::shiftLeft);
        System.out.println(); 
        showFullShift(RIGHT, CycleShifter::shiftRight);
    }
    
    
    private static void showFullShift(String dir, Shifter function)
    {
        for (int i = 1; i <= numbersLen; ++i)
        {
            function.shift(numbers);
            System.out.printf(FORMAT, i, dir, "" + numbers);       
        }
    }
    
    
    private static void shiftLeft(ArrayList<Integer> A)
    {
        int N = A.size();
        
        if (N == 0)
        {
            return;
        }
        
        int tmp = A.get(0);
        
        for (int i = 0; i < N - 1; ++i)
        {
            A.set(i, A.get(i + 1));
        }
        
        A.set(N - 1, tmp);
    }
    
    
    private static void shiftRight(ArrayList<Integer> A)
    {
        int N = A.size();
        
        if (N == 0)
        {
            return;
        }
        
        int tmp = A.get(N - 1);
        
        for (int i = N - 2; i >= 0; --i)
        {
            A.set(i + 1, A.get(i));
        }
        
        A.set(0, tmp);
    }
}