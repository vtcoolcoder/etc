public class LambdaNewer
{ 
    public interface Function
    {
        public double run(int n);
    }
   
   
    public static void main(String[] args)
    {
        highOrderMethod(
            LambdaNewer::showGreeting,
            LambdaNewer::showArithmeticProgression,
            LambdaNewer::showGeometicProgression,
            LambdaNewer::showGoodBye
        );
        
        showCustomProgression(1000, 1101, 1,
            LambdaNewer::getSelf,
            LambdaNewer::getSqr,
            LambdaNewer::getCube,
            LambdaNewer::getSqrt,
            LambdaNewer::getCubeRoot,
            LambdaNewer::getPentaRoot,
            LambdaNewer::getSeptaRoot
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
        System.out.println("Hello, world!");
    }
    
    
    private static void showGoodBye()
    {
        System.out.println("Good bye!");
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
    
    
    private static double getSelf(int n)
    {
        return n;
    }
    
    
    private static double getSqr(int n)
    {
        return n*n;
    }
    
    
    private static double getCube(int n)
    {
        return n*n*n;
    }
    
    
    private static double getSqrt(int n)
    {
        return Math.sqrt(n);
    }
    
    
    private static double getCubeRoot(int n)
    {
        return Math.pow(n, 1.0/3.0);
    }
    
    
    private static double getPentaRoot(int n)
    {
        return Math.pow(n, 1.0/5.0);
    }
    
    
    private static double getSeptaRoot(int n)
    {
        return Math.pow(n, 1.0/7.0);
    }
}