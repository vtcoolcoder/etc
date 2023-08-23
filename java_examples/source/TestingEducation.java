import java.util.ArrayList;


public class TestingEducation
{
    private boolean passed;
    
    
    public static void main(String[] args)
    {
        showResult();
    }
    
    
    private static ArrayList<boolean[]> getAllTestCases()
    {
        ArrayList<boolean[]> result = new ArrayList<boolean[]>();
        
        for (byte first = 0; first <= 1; ++first)
        {
            for (byte second = 0; second <= 1; ++second)
            {
                for (byte third = 0; third <= 1; ++third)
                {
                    for (byte fourth = 0; fourth <= 1; ++fourth)
                    {
                        for (byte fiveth = 0; fiveth <= 1; ++fiveth)
                        {
                            result.add(new boolean[] {
                                           convertByteToBool(first),
                                           convertByteToBool(second),
                                           convertByteToBool(third),
                                           convertByteToBool(fourth),
                                           convertByteToBool(fiveth) });  
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    
    private static boolean convertByteToBool(byte converted)
    {
        return converted == 1 ? true : false;
    }
    
    
    private static void showResult()
    {
        for (boolean[] currentCasesList: getAllTestCases())
        {
            System.out.println("Current testing cases:");
            
            for (byte i = 0; i < currentCasesList.length; ++i)
            {
                System.out.println("\tCase №" + (i + 1) + ": " + currentCasesList[i]);
            }
            
            System.out.println("Testing results: " + 
                new TestingEducation(currentCasesList));
            System.out.println();
        }
    }
    
    
    public TestingEducation(boolean[] casesList)
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