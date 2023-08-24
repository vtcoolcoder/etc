import java.util.*;


public class TestingEducation
{
    private boolean passed;
    
    
    public static void main(String[] args)
    {
        showResult(Integer.parseInt(args[0]));
    }
    
    
    private static int convertDecToBin(int converted)
    {
        int result = 0;
           
        for (int count = 0; converted > 0; converted >>= 1, ++count)
        {
            result += (converted % 2) * ((int) Math.pow(10, count));
        }
       
        return result;
    }
    
    
    private static ArrayList<Boolean> convertPsevdoBinToBoolList(int converted)
    {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        ArrayList<Boolean> buffer = new ArrayList<Boolean>();
        
        if (converted == 0)
        {
            result.add(convertByteToBool((byte)0));
            return result;
        }
               
        for (; converted > 0; converted /= 10)
        {
            result.add(convertByteToBool((byte)(converted % 10)));
        }
        
        
        for (int i = result.size() - 1; i >= 0; --i)
        {
            buffer.add(result.get(i));
        }
        
        result = buffer;
        
        return result;
    }
    
    
    private static ArrayList<Boolean> getBinaryList(int quantity, int iterNumber,
                                                    int iterCount)
    {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        ArrayList<ArrayList<Boolean>> buffer = new ArrayList<ArrayList<Boolean>>();
        
        for (int i = 0; i < iterCount; ++i)
        {
            for (int j = 0; j < quantity; ++j)
            {        
                buffer.add(convertPsevdoBinToBoolList(
                               convertDecToBin(iterNumber)));
            }
        }
        
        result = buffer.get(iterNumber); 
            
        return result;
    }
    
    
    private static ArrayList<ArrayList<Boolean>> getAllTestCases(int casesQuantity)
    {    
        ArrayList<ArrayList<Boolean>> result = 
            new ArrayList<ArrayList<Boolean>>();
        
        int iterCount = (int) Math.pow(2, casesQuantity);
        
        for (int i = 0; i < iterCount; ++i)
        { 
            result.add(getBinaryList(casesQuantity, i, iterCount));
        }

        return result;
    }
    
    
    private static boolean convertByteToBool(byte converted)
    {
        return converted == 0 ? false : true;
    }
    
    
    private static void showResult(int quantity)
    { 
        for (var currentCasesList: getAllTestCases(quantity))
        {
            System.out.println("Current testing cases:");
                      
            for (int i = 0; i < currentCasesList.size(); ++i)
            {
                System.out.println("\tCase №" + (i + 1) + ": " + 
                    currentCasesList.get(i));
            }
            
            System.out.println("Testing results: " + 
                new TestingEducation(currentCasesList));
            System.out.println();
        }
    }
    
    
    public TestingEducation(ArrayList<Boolean> casesList)
    {
        passed = true;
        
        for (boolean currentCase: casesList)
        {
            passed &= currentCase;
        }
    }
    
    
    public String toString()
    {
        return passed ? "OK" : "FAIL";
    }
}