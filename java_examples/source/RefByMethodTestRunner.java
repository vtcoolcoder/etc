public class RefByMethodTestRunner
{
    public static void main(String[] args)
    {
        ((Runnable)RefByMethodTest::hello).run();
    }
}