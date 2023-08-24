import java.util.*;


public class TestingEducation
{
    private static final String ERROR = "Задайте 1 целочисленный параметр больше 0!";
    private static final String UNKNOWN_ERROR = "Неизвестная ошибка!";
    private static final String ADDED_SYMBOL = "0";
    
    private static final int LIMIT_ARGS = 1;
    private static final int ONLY_ONE_ARG = 0;
    private static final int FAIL_EXIT_CODE = 1;
    private static final int DEGREE_BASE = 2;

    private int casesQuantity;
    private int iterCount;
           
    
    public static void main(String[] args)
    {
        if (args.length != LIMIT_ARGS)
        {     
            showErrorMessage(ERROR);
        }
        
        try
        {
            int casesQuantity = Integer.parseInt(args[ONLY_ONE_ARG]);
            new TestingEducation(casesQuantity).showResult();
        }
        catch (NumberFormatException ex)
        {
            showErrorMessage(ERROR);
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
    
    
    public TestingEducation(int casesQuantity)
    {   
        this.casesQuantity = casesQuantity;
        iterCount = (int) Math.pow(DEGREE_BASE, casesQuantity);   
    }
    
    
    public void showResult()
    {
        System.out.println();
        int allTestCasesIter = 0;
           
        for (var currentCasesList: getAllTestCases())
        {
            System.out.println("Current testing cases:");
                      
            for (int i = 0; i < currentCasesList.size(); ++i)
            {
                System.out.println("\tCase №" + (i + 1) + ": " + 
                    currentCasesList.get(i));
            }
            
            System.out.println("Testing results: " + getResult(currentCasesList));
            
            if (allTestCasesIter < iterCount - 1)
            {
                System.out.println();
            }
            
            allTestCasesIter++;
        }
    }
    
    
    private ArrayList<ArrayList<Boolean>> getAllTestCases()
    {    
        var result = new ArrayList<ArrayList<Boolean>>();
        
        for (int i = 0; i < iterCount; ++i)
        { 
            result.add(getBinaryList(i));
        }

        return result;
    }
    
    
    private ArrayList<Boolean> getBinaryList(int iterNumber)
    {
        var result = new ArrayList<Boolean>();
        var buffer = new ArrayList<ArrayList<Boolean>>();
        String cached = "";
        
        for (int i = 0; i < iterCount; ++i)
        {
            for (int j = 0; j < casesQuantity; ++j)
            {   
                cached = "" + convertDecToBin(iterNumber);        
                cached = addZeroDigitSymbolIntoBegin(cached);        
                buffer.add(convertStringToBoolList(cached));   
            }
        }
        
        result = buffer.get(iterNumber); 
        return result;
    }
    
    
    private int convertDecToBin(int converted)
    {
        int result = 0;
           
        for (int count = 0; converted > 0; converted >>= 1, ++count)
        {
            result += (converted % DEGREE_BASE) * ((int) Math.pow(10, count));
        }
       
        return result;
    }
    
    
    private String addZeroDigitSymbolIntoBegin(String str)
    {    
        int strSize = str.length();
             
        if (casesQuantity == strSize)
        {
            return str;
        }
        
        String result = "";   
        
        for (int i = 0; i < casesQuantity - strSize; ++i)
        {
            result += ADDED_SYMBOL;
        }
        
        result += str;
        
        return result;
    }
    
  
    private ArrayList<Boolean> convertStringToBoolList(String converted)
    {
        var result = new ArrayList<Boolean>();
    
        for (char currentSymbol: converted.toCharArray())
        {
            result.add(convertCharToBool(currentSymbol));
        }
        
        return result;
    }
    
    
    private boolean convertCharToBool(char converted)
    {
        return converted == '0' ? false : true;
    }
    
       
    private String getResult(ArrayList<Boolean> casesList)
    {
        boolean passed = true;
        
        for (boolean currentCase: casesList)
        {
            passed &= currentCase;
        }
        
        return passed ? "OK" : "FAIL";
    }
}