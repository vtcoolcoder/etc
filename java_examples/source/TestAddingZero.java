

public class TestAddingZero
{
    private static final String[] TEST_CASE1 = {"0", "1"};
    private static final String[] TEST_CASE2 = {"0", "1", "10", "11"};
    private static final String[] TEST_CASE3 = 
    {
        "0", "1", "10", "11", "100", "101", "110", "111"
    };
    private static final String[] TEST_CASE4 = 
    {
        "0", "1", "10", "11", "100", "101", "110", "111",
        "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
    };
    private static final String[][] ALL_CASES =
    {
        TEST_CASE1, TEST_CASE2, TEST_CASE3, TEST_CASE4
    };
    
    
    public static void main(String[] args)
    {
        for (String[] currentCaseList: ALL_CASES)
        {
            for (String currentCase: currentCaseList)
            {
                int size = currentCaseList.length;
                System.out.println(addZeroDigitSymbolIntoBegin(currentCase, size));
            }
        }
    }
    

    private static String addZeroDigitSymbolIntoBegin(String str, 
                                                      final int containerSize)
    {    
        int quantity = log2(containerSize);  
        int strSize = str.length();
             
        if (quantity == strSize)
        {
            return str;
        }
        
        String result = "";
        final String ADDED = "0";      
        
        for (int i = 0; i < quantity - strSize; ++i)
        {
            result += ADDED;
        }
        
        result += str;
        
        return result;
    }
    
    
    private static int log2(final int n)
    {
        final int BASE = 2;
        int result = 0;       
        
        for (int buffer = 0; buffer < n; )
        {
            buffer = (int) Math.pow(BASE, ++result);
        }
        
        return result;
    }
}