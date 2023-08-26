public class LambdaNewer
{    
    public static void main(String[] args)
    {
        highOrderMethod(
            LambdaNewer::showGreeting,
            LambdaNewer::showArithmeticProgression,
            LambdaNewer::showGeometicProgression,
            LambdaNewer::showGoodBye
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
}