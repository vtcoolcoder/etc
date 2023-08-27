

public class LambdaNewer
{ 
    private static final String GREETING = "Hello, world!";
    private static final String BYE = "Good bye!";

    private static final double SQR = 1.0/2.0;
    private static final double CUBE = 1.0/3.0;
    private static final double PENTA = 1.0/5.0;
    private static final double SEPTA = 1.0/7.0;
    private static final double UNDECIMA = 1.0/11.0;
    private static final double TERCDECIMA = 1.0/13.0;
   
    
    public interface Function
    {
        public double run(double n);
    }
   
   
    public static void main(String[] args)
    {
        highOrderMethod(
            LambdaNewer::showGreeting,
            LambdaNewer::showArithmeticProgression,
            LambdaNewer::showGeometicProgression,
            LambdaNewer::showGoodBye
        );
        
        showCustomProgression(0, 21, 1,
            LambdaNewer::getSelf,
            LambdaNewer::getSqr,
            LambdaNewer::getCube);
            
        showCustomProgression(1_000_000, 10_000_000, 500_000,    
            LambdaNewer::getSqrt,
            LambdaNewer::getCubeRoot,
            LambdaNewer::getPentaRoot,
            LambdaNewer::getSeptaRoot,
            LambdaNewer::getUndecimaRoot,
            LambdaNewer::getTercdecimaRoot
        );
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
              
            for (int i = start; i < stop; i += step)
            {
                System.out.print(function.run(i) + "    ");
                if (i % 4 == 0)
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
    
    
    private static double getRoot(double base, double degree)
    {
        return Math.pow(base, degree);
    }
    
    
    private static double getSqrt(double n)
    {
        return getRoot(n, SQR);
    }
    
    
    private static double getCubeRoot(double n)
    {
        return getRoot(n, CUBE);
    }
    
    
    private static double getPentaRoot(double n)
    {
        return getRoot(n, PENTA);
    }
    
    
    private static double getSeptaRoot(double n)
    {
        return getRoot(n, SEPTA);
    }
    
    
    private static double getUndecimaRoot(double n)
    {
        return getRoot(n, UNDECIMA);
    }
    
    
    private static double getTercdecimaRoot(double n)
    {
        return getRoot(n, TERCDECIMA);
    }
}