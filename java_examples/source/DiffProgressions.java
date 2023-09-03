import java.util.*;


public class DiffProgressions
{
    public static void main(String[] args)
    {
        showResult(
            generateArithmeticProgression(-10, 11, 2),
            generateArithmeticProgression(10, -11, -2),
            generateGeometricProgression(2, 1000, 2),
            generateGeometricProgression(512, 2, -2),
            generateArithmeticGeoProgression(2, 2000, 2, 1),
            generateArithmeticGeoProgression(1535, 2, -2, -1));
    }
    
    
    private static void showResult(ArrayList<Integer> ... lists)
    {
        for (var list : lists)
        {
            System.out.println(list);
        }
    }
    
    
    private static ArrayList<Integer> 
    generateArithmeticProgression(final int start, final int stop, final int step)
    {
        ArrayList<Integer> result = new ArrayList<>();
        int offset = (stop - start + 1) / step;
        
        for (int i = start, j = 0; 
             stop >= 0 ? j < offset : j >= -offset; 
             i += step, j += (stop >= 0 ? 1 : -1))
        {
            result.add(i);
        }
        
        return result;
    }
    
    
    private static ArrayList<Integer> 
    generateGeometricProgression(final int start, final int stop, final int step)
    {
        ArrayList<Integer> result = new ArrayList<>();
        
        for (int i = start; 
             step >= 0 ? i < stop : i >= stop; 
             i *= (step >= 0 ? step : (1.0 / -step)))
        {
            result.add(i);
        }
        
        return result;
    }
    
    
    private static ArrayList<Integer> 
    generateArithmeticGeoProgression(final int start, final int stop, 
                                     final int step, final int added)
    {
        ArrayList<Integer> result = new ArrayList<>();
        
        for (int i = start; 
             step >= 0 ? i < stop : i >= stop; 
             i *= (step >= 0 ? step : (1.0 / -step)), i += added)
        {
            result.add(i);
        }
        
        return result;
    }
}