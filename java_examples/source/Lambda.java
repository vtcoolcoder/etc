
public class Lambda
{
    private static final char DOT = '.';
    
    private static final String CALLED = "Вызвана ";
    private static final String LAMBDA = "явно лямбда-";
    private static final String FUNCTION = "функция"; 
    private static final String TAIL = LAMBDA + FUNCTION + DOT;
    private static final String FIRST_FUN = CALLED + "первая " + TAIL;
    private static final String SECOND_FUN = CALLED + "вторая " + TAIL;
    private static final String THIRD_FUN = CALLED + "третья " + TAIL;
    private static final String PERSON = CALLED + "написанная " + FUNCTION + ": ";

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
        System.out.println(PERSON + "runFirstFunction()" + DOT);      
    }
    
    
    private void runSecondFunction()
    {
        System.out.println(PERSON + "runSecondFunction()" + DOT);
    }
    
    
    private void runThirdFunction()
    {
        System.out.print(PERSON + "runThirdFunction()" + DOT);
    }
}
