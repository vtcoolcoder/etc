

public class NumberToCharConverter
{     
    private static char convertNumberToChar(final int inputedNumber)
    {
        final int OFFSET = 48;
        return (char) (inputedNumber + OFFSET);
    }
    
    
    public static void main(String[] args)
    {   
        System.out.println(convertNumberToChar(Integer.parseInt(args[0])));
    }
}