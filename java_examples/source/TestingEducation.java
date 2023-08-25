import java.util.*;


public class TestingEducation
{
    private static final int ARGS_LIMIT = 1;
    private static final int ONLY_ONE_ARG = 0;
    private static final int FAIL_EXIT_CODE = 1;
    private static final int BINARY_DEGREE_BASE = 2;
    private static final int MIN_ARG_VALUE = 1;
    private static final int MAX_ARG_VALUE = 22; 
    
    private static final String INVALID_ARG_ERROR = 
        "Задайте 1 целочисленный параметр в диапазоне от " + MIN_ARG_VALUE +
        " до " + MAX_ARG_VALUE + " -- включительно!";
    private static final String UNKNOWN_ERROR = "Неизвестная ошибка!";
    private static final String ADDED_SYMBOL = "0";
    private static final String CURRENT_TESTING_CASES = "Current testing cases:";
    private static final String CASE_NUMBER = "\tCase №";
    private static final String TESTING_RESULTS = "Testing results: ";
    private static final String SUCCESS_RESULT = "OK";
    private static final String FAIL_RESULT = "FAIL";
    
    
    private int casesQuantity;
    private int iterCount;
    
    private ArrayList<ArrayList<Boolean>> allTestCases;
           
    
    public static void main(String[] args)
    {
        if (!isValidArgsLimit(args.length))
        {     
            showErrorMessage(INVALID_ARG_ERROR);
        }
        
        try
        {
            int casesQuantity = Integer.parseInt(args[ONLY_ONE_ARG]);
            
            if (!isValidArgRange(casesQuantity))
            {
                showErrorMessage(INVALID_ARG_ERROR);
            }
            
            new TestingEducation(casesQuantity).showResult();
        }
        catch (NumberFormatException ex)
        {
            showErrorMessage(INVALID_ARG_ERROR);
        }
        catch (Exception ex)
        {
            showErrorMessage(UNKNOWN_ERROR);
        }
    }
    
    
    private static void showErrorMessage(String msg)
    {
        System.err.println(msg);
        System.exit(FAIL_EXIT_CODE);
    }
    
    
    private static boolean isValidArgsLimit(int checkedNumber)
    {
        return (checkedNumber == ARGS_LIMIT);
    }
    
    
    private static boolean isValidArgRange(int checkedNumber)
    {
        return (checkedNumber >= MIN_ARG_VALUE) && 
               (checkedNumber <= MAX_ARG_VALUE);
    }
    
    
    public TestingEducation(int casesQuantity)
    {   
        this.casesQuantity = casesQuantity;
        iterCount = (int) Math.pow(BINARY_DEGREE_BASE, casesQuantity);         
        initAllTestCases();
    }
    
        
    public int getIterCount()
    {
        return iterCount;
    }
    
    
    public void showResult()
    {
        System.out.println();
        int allTestCasesIter = 0;
           
        for (var currentCasesList: getAllTestCases())
        {
            System.out.println(CURRENT_TESTING_CASES);
                      
            for (int i = 0; i < currentCasesList.size(); ++i)
            {
                System.out.println(CASE_NUMBER + (i + 1) + ": " + 
                    currentCasesList.get(i));
            }
            
            System.out.println(TESTING_RESULTS + getResult(currentCasesList));
            
            if (allTestCasesIter < iterCount - 1)
            {
                System.out.println();
            }
            
            allTestCasesIter++;
        }
    }
    
    
    private void initAllTestCases()
    {
        allTestCases = new ArrayList<ArrayList<Boolean>>();
        
        for (int i = 0; i < iterCount; ++i)
        {                     
            allTestCases.add(
                convertStringToBoolList(
                insertZeroDigitSymbolIntoBegin(
                convertDecToBinString(i))));        
        }
    }
    
    
    private ArrayList<ArrayList<Boolean>> getAllTestCases()
    {
        return allTestCases;
    }
    
    
    private StringBuilder convertDecToBinString(int converted)
    {
        var result = new StringBuilder();
        var buffer = new ArrayList<Character>(); 
           
        for ( ; converted > 0; converted >>= 1)
        {
            int currentDigit = (converted % BINARY_DEGREE_BASE); 
            char currentSymbol = ("" + currentDigit).charAt(0); 
            buffer.add(currentSymbol);
        }
        
        for (int i = buffer.size() - 1; i >= 0; i--)
        {
            char currentSymbol = buffer.get(i);
            result.append(currentSymbol);
        }
         
        return result;
    }
    
    
    private StringBuilder insertZeroDigitSymbolIntoBegin(StringBuilder str)
    {    
        int strSize = str.length();
        var result = new StringBuilder();   
             
        if (casesQuantity == strSize)
        {
            return str;
        }      
        
        for (int i = 0; i < casesQuantity - strSize; ++i)
        {
            result.append(ADDED_SYMBOL);
        }
        
        result.append(str);
        
        return result;
    }
    
  
    private ArrayList<Boolean> convertStringToBoolList(StringBuilder converted)
    {
        var result = new ArrayList<Boolean>();
    
        for (char currentSymbol: converted.toString().toCharArray())
        {
            result.add(convertCharToBool(currentSymbol));
        }
        
        return result;
    }
    
    
    private boolean convertCharToBool(char converted)
    {
        return (converted == '0') ? false : true;
    }
    
       
    private String getResult(ArrayList<Boolean> casesList)
    {
        boolean passed = true;
        
        for (boolean currentCase: casesList)
        {
            passed &= currentCase;
        }
        
        return passed ? SUCCESS_RESULT : FAIL_RESULT;
    }
}