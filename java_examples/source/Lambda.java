
public class Lambda
{
    private static final char DOT = '.';
    
    private static final String CALLED = "Вызвана ";
    private static final String LAMBDA = "явно лямбда-";
    private static final String FUNCTION = "функция"; 
    private static final String FIRST_FUN = 
        CALLED + "первая " + LAMBDA + FUNCTION + DOT;
    private static final String SECOND_FUN = 
        CALLED + "вторая " + LAMBDA + FUNCTION + DOT;
    private static final String THIRD_FUN = 
        CALLED + "третья " + LAMBDA + FUNCTION + DOT;
    private static final String CALLED_PERSON = 
        CALLED + "написанная " + FUNCTION + ": ";

    private static final Runnable[] FUNCTIONS_1 =
    {
        () -> System.out.println(FIRST_FUN),
        () -> System.out.println(SECOND_FUN),
        () -> System.out.println(THIRD_FUN)
    };
    
    private final Runnable[] FUNCTIONS_2 =
    {
        () -> runFirstFunction(),
        () -> runSecondFunction(),
        () -> runThirdFunction()
    };
    
    private final Runnable[][] FUNCTIONS = 
    {
        FUNCTIONS_1,
        FUNCTIONS_2
    };

  
    public static void main(String[] args)
    {
        new Lambda();    
    }
    
    
    public Lambda()
    {
        for (Runnable[] currentFunctionsList: FUNCTIONS)
        {
            testHighOrderFunc(currentFunctionsList);
            System.out.println();
        }
    }
    
    
    private void testHighOrderFunc(Runnable ... funcs)
    {
        for (Runnable f: funcs)
        {
            f.run();
        }
    }
    
    
    private void runFirstFunction()
    {
        System.out.println(CALLED_PERSON + "runFirstFunction()" + DOT);
        
    }
    
    
    private void runSecondFunction()
    {
        System.out.println(CALLED_PERSON + "runSecondFunction()" + DOT);
    }
    
    
    private void runThirdFunction()
    {
        System.out.print(CALLED_PERSON + "runThirdFunction()" + DOT);
    }
}
