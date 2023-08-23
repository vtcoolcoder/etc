import java.util.ArrayList;


public class DecimalToBinaryConverter
{
    public static void main(String[] args)
    {
        for (int i = 0; i <= 31; ++i)
        {
            int cached = convertDecToBin(i);
            System.out.print(i + " -> " + cached);
            System.out.println(" -> " + convertPsevdoBinToByteList(cached));
        }
    }
    
    
    private static int convertDecToBin(int converted)
    {
        int result = 0;
           
        for (int count = 0; converted > 0; converted /= 2, ++count)
        {
            result += (converted % 2) * ((int) Math.pow(10, count));
        }
       
        return result;
    }
    
    
    private static ArrayList<Byte> convertPsevdoBinToByteList(int converted)
    {
        ArrayList<Byte> result = new ArrayList<Byte>();
        ArrayList<Byte> buffer = new ArrayList<Byte>();
        
        if (converted == 0)
        {
            result.add((byte)0);
            return result;
        }
        
        for (; converted > 0; converted /= 10)
        {
            result.add((byte)(converted % 10));
        }
        
        
        for (int i = result.size() - 1; i >= 0; --i)
        {
            buffer.add(result.get(i));
        }
        
        result = buffer;
        
        return result;
    }
}

