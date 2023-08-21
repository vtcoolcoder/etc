public class Lambda
{
    private static final Runnable[] FUNCTIONS =
    {
        () -> System.out.println("Первая функция."),
        () -> System.out.println("Вторая функция."),
        () -> System.out.println("Третья функция.")
    };


    private static void testHighOrderFunc(Runnable ... funcs)
    {
        for (Runnable f: funcs)
        {
            f.run();
        }
    }
    
    
    public static void main(String[] args)
    {
        testHighOrderFunc(FUNCTIONS);
    }
}