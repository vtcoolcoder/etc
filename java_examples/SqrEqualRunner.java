

public class SqrEqualRunner
{
    private static final double LIMIT = 9.66996;
    private static final double INC = 0.699669;
    
    private static final boolean ON_FROM_LOOP = true;
    private static final boolean OFF_FROM_LOOP = false;
    
    private static final boolean ON_SHOW = true;
    private static final boolean OFF_SHOW = false;
    
    private static SqrEqual.ConstructorParams params;
    private static boolean isFromLoop; 


    public static void main(String[] args)
    {
        runLoop();     
        showResult();
    }
    
    
    private static void runLoop()
    {
        setIsFromLoop(ON_FROM_LOOP);
        
        for (double i = -LIMIT; i <= LIMIT; i += INC)
        {
            for (double j = -LIMIT; j <= LIMIT; j += INC)
            {
                for (double k = -LIMIT; k <= LIMIT; k += INC)
                {
                    packParams(i, j, k); 
                    showResult();
                }
            }
        }
        
        setIsFromLoop(OFF_FROM_LOOP);
    }
    
    
    private static void packParams(double i, double j, double k)
    {  
        params = new SqrEqual.ConstructorParams();     
                        
        params.setA(i);
        params.setB(j);
        params.setC(k);
        params.setIsShowABCD(ON_SHOW);
        params.setIsShowGreeting(OFF_SHOW);
        params.setIsDebugOn(OFF_SHOW);
    }
    
    
    private static void showResult()
    {   
        System.out.println(
            isFromLoop() ? 
                new SqrEqual(params) : new SqrEqual());
        
        System.out.println();
    }
    
    
    private static void setIsFromLoop(boolean aIsFromLoop)
    {
        isFromLoop = aIsFromLoop;
    }
    
    
    private static boolean isFromLoop()
    {
        return isFromLoop;
    }
}