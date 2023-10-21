// Создать второй поток исполнения, расширив класс Thread
class NewerThread extends Thread
{
    NewerThread()
    {
        // создать новый поток исполнения
        super("Демонстрационный поток.");
        System.out.println("Дочерний поток: " + this);
        start(); // запустить поток на исполнение
    }
    
    // Точка входа во второй поток исполнения
    public void run()
    {
        try
        {
            for (int i = 5; i > 0; i--)
            {
                System.out.println("Дочерний поток: " + i);
                Thread.sleep(500);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Дочерний поток прерван.");
        }
        
        System.out.println("Дочерний поток завершён.");
    }
}

public class ExtendThread
{
    public static void main(String[] args)
    {
        new NewerThread(); // создать новый поток исполнения
        
        try
        {
            for (int i = 5; i > 0; i--)
            {
                System.out.println("Главный поток: " + i);
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Главный поток прерван.");
        }
        
        System.out.println("Главный поток завершён.");
    }
}