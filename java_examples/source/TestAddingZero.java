

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
                int size = log2(currentCaseList.length);
                System.out.println(addZeroDigitSymbolIntoBegin(currentCase, size));
            }
        }
    }
    
// 
// 
// int quantity = log2(currentCaseList.length)
// 2 -> 1 
// 4 -> 2 
// 8 -> 3 
// 16 -> 4 
// Math.log(x, 2)    
/*
* quantity = 1:
* 0 -> "0" 
* 1 -> "1" 
*
* quantity = 2:
* 0 -> "00"     добавить "0" (quantity - strSize) раз
* 1 -> "01"     добавить "0" (quantity - strSize) раз
* 10 -> "10" 
* 11 -> "11" 
*
* quantity = 3:
* 0 -> "000"     добавить "0" (quantity - strSize) раз
* 1 -> "001"     добавить "0" (quantity - strSize) раз
* 10 -> "010"    добавить "0" (quantity - strSize) раз
* 11 -> "011"    добавить "0" (quantity - strSize) раз
* 100 -> "100" 
* 101 -> "101" 
* 110 -> "110" 
* 111 -> "111" 
*
* quantity = 4:
* 0 -> "0000"     добавить "0" (quantity - strSize) раз
* 1 -> "0001"     добавить "0" (quantity - strSize) раз
* 10 -> "0010"    добавить "0" (quantity - strSize) раз
* 11 -> "0011"    добавить "0" (quantity - strSize) раз
* 100 -> "0100"   добавить "0" (quantity - strSize) раз
* 101 -> "0101"   добавить "0" (quantity - strSize) раз
* 110 -> "0110"   добавить "0" (quantity - strSize) раз
* 111 -> "0111"   добавить "0" (quantity - strSize) раз
* 1000 -> "1000" 
* 1001 -> "1001"
* 1010 -> "1010"
* 1011 -> "1011"
* 1100 -> "1100"
* 1101 -> "1101"
* 1110 -> "1110"
* 1111 -> "1111"
*
*/

    private static String addZeroDigitSymbolIntoBegin(String str, final int quantity)
    {      
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
    
    
    private static int log2(int n)
    {
        final int BASE = 2;
        int result = 0;
        int buffer = 0;
        
        for (; ; ++result)
        {
            buffer = (int) Math.pow(BASE, result);
            if (buffer == n)
            {
                break;
            }
        }
        
        return result;
    }
}