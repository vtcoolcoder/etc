public class LambdaNewer
{ 
    public interface Function
    {
        double run(double t);
    }
    
    private static final String GREETING = "Hello, world!";
    private static final String BYE = "Good bye!";

    private static final double SQR_ROOT = getRelation(2);
    private static final double CUBE_ROOT = getRelation(3);
    private static final double PENTA_ROOT = getRelation(5);
    private static final double SEPTA_ROOT = getRelation(7);
    private static final double UNDECIMA_ROOT = getRelation(11);
    private static final double TERCDECIMA_ROOT = getRelation(13);
    
    private static final int LENGTH = 20;
    private static final int ROOT_START = 1_000_000;
    private static final int ROOT_STEP = 500_000;
    private static final int ROOT_STOP = getStop(LENGTH, ROOT_START, ROOT_STEP);
    private static final int DEGREE_START = 0;
    private static final int DEGREE_STEP = 1;
    private static final int DEGREE_STOP = LENGTH;        
     
     
    private static final Runnable[] VOID_FUNCTIONS =
    {
        LambdaNewer::showGreeting,
        LambdaNewer::showArithmeticProgression,
        LambdaNewer::showGeometicProgression,
        LambdaNewer::showGoodBye
    };
    
    private static final Function[] DEGREE_FUNCTIONS =
    {    
        LambdaNewer::getSelf,
        LambdaNewer::getSqr,
        LambdaNewer::getCube
    };   
    
    private static final Function[] ROOT_FUNCTIONS =
    {
        LambdaNewer::getSqrt,
        LambdaNewer::getCubeRoot,
        LambdaNewer::getPentaRoot,
        LambdaNewer::getSeptaRoot,
        LambdaNewer::getUndecimaRoot,
        LambdaNewer::getTercdecimaRoot
    };
    
      
    public static void main(String[] args)
    {
        highOrderMethod(VOID_FUNCTIONS);
        showCustomProgression(
            DEGREE_START, DEGREE_STOP, DEGREE_STEP, DEGREE_FUNCTIONS);       
        showCustomProgression(ROOT_START, ROOT_STOP, ROOT_STEP, ROOT_FUNCTIONS);   
    }
    
    
    private static double getRelation(double n)
    {
        return 1./n;
    }
    
    
    private static int getStop(int length, int start, int step)
    {
        return step*(length + 1) + start;
    }
    
    
    private static void highOrderMethod(Runnable ... methods)
    {
        for (Runnable currentMethod: methods)
        {
            currentMethod.run();
        }
    }
    
    
    private static void showGreeting()
    {
        System.out.println(GREETING);
    }
    
    
    private static void showGoodBye()
    {
        System.out.println(BYE);
    }
    
    
    private static void showArithmeticProgression()
    {
        for (int i = 0; i < 10; ++i)
        {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    
    private static void showGeometicProgression()
    {
        for (int i = 0; i < 10; ++i)
        {
            System.out.print(i*i + " ");
        }
        System.out.println();
    }
    
    
    private static void showCustomProgression(int start, int stop, int step, 
                                              Function ... functions)
    {
        for (Function function: functions)
        {
            System.out.println();
              
            for (double i = start; i < stop; i += step)
            {
                System.out.print(function.run(i) + "    ");       
                int offset = ((int)i - start) / step;  
                if (++offset % 4 == 0)
                {
                    System.out.println();
                }
            }
            
            System.out.println();
        }
    }
    
    
    private static double getSelf(double n)
    {
        return n;
    }
    
    
    private static double getSqr(double n)
    {
        return n*n;
    }
    
    
    private static double getCube(double n)
    {
        return n*n*n;
    }
    
      
    private static double getSqrt(double n)
    {
        return Math.pow(n, SQR_ROOT);
    }
    
    
    private static double getCubeRoot(double n)
    {
        return Math.pow(n, CUBE_ROOT);
    }
    
    
    private static double getPentaRoot(double n)
    {
        return Math.pow(n, PENTA_ROOT);
    }
    
    
    private static double getSeptaRoot(double n)
    {
        return Math.pow(n, SEPTA_ROOT);
    }
    
    
    private static double getUndecimaRoot(double n)
    {
        return Math.pow(n, UNDECIMA_ROOT);
    }
    
    
    private static double getTercdecimaRoot(double n)
    {
        return Math.pow(n, TERCDECIMA_ROOT);
    }
}