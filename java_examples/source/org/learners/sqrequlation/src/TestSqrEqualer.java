package org.learners.sqrequlation;


class TestSqrEqualer
{
    private static final double LIMIT = 1;
    private static final double INCREMENT = 1;
    
    private static final String TESTING = "Тестирование ";
    private static final String P = "...";
    private static final String EXCEPTIONS = "исключений" + P;
    private static final String BASE_CASES = "базовых вариантов" + P;
    private static final String PREFIX = "\t-- ";
    private static final String USER_INPUT = PREFIX + "с пользовательским вводом:";
    private static final String AVTO = PREFIX + "автоперебором:";
    
    private static final SqrEqualer[] TEST_CASES =
    {
        new SqrEqualer(0, 0, 0),
        new SqrEqualer(10, 0, 0),
        new SqrEqualer(0, 10, 0),
        new SqrEqualer(0, 0, 10),
        new SqrEqualer(10, 1, 10)
    };
    
    
    public static void main(String[] args)
    {
        runTesting();
    }
    
    
    private static void runTesting()
    {
        System.err.println(TESTING + EXCEPTIONS);
        System.err.println();
        
        showExceptionTestedContent(TEST_CASES);
        
        System.err.println(TESTING + BASE_CASES);
        System.err.println(USER_INPUT);
        showResult();
        
        System.err.println();
        System.err.println(AVTO);     
        System.err.println();      
        runLoop();
    }
    
    
    private static void showExceptionTestedContent(SqrEqualer ... sqrEqualers)
    {  
        for (SqrEqualer aSqrEqualer : sqrEqualers)
        {
            double[] abc = aSqrEqualer.getAbc();
            String aSqrEqualerInfo =
                "( a = " + abc[SqrEqualer.A] + 
                ", b = " + abc[SqrEqualer.B] + 
                ", c = " + abc[SqrEqualer.C] + " ): ";
                
            System.err.print(aSqrEqualerInfo);
            System.out.println(aSqrEqualer);
            System.err.println();
        }
    }
    
    
    private static void showResult()
    {  
        packParams();        
        System.out.println(new SqrEqualer());
        System.out.println();
    }
    
    
    private static void packParams()
    {
        SqrEqualer.getGlobalParams().setIsShowGreeting(SqrEqualer.ON);
        SqrEqualer.getGlobalParams().setIsShowD(SqrEqualer.ON);     
    }
    
    
    private static void runLoop()
    {
        for (double i = -LIMIT; i <= LIMIT; i += INCREMENT)
        {
            for (double j = -LIMIT; j <= LIMIT; j += INCREMENT)
            {
                for (double k = -LIMIT; k <= LIMIT; k += INCREMENT)
                {                 
                    showResult(i, j, k);
                }
            } 
        }     
    }
    
    
    private static void showResult(double a, double b, double c)
    {
        packParams(a, b, c);
        System.out.println(new SqrEqualer(SqrEqualer.getGlobalParams()));
        System.out.println();
    }
    
    
    private static void packParams(double a, double b, double c)
    {
        SqrEqualer.getGlobalParams().setA(a);
        SqrEqualer.getGlobalParams().setB(b);
        SqrEqualer.getGlobalParams().setC(c);  
        SqrEqualer.getGlobalParams().setIsShowGreeting(SqrEqualer.OFF);
        SqrEqualer.getGlobalParams().setIsShowD(SqrEqualer.OFF);
        SqrEqualer.getGlobalParams().setIsShowABCD(SqrEqualer.ON);
    }   
}



