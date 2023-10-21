// Создать второй поток исполнения
class NewThread implements Runnable
{
    Thread t;
    
    NewThread() 
    {
        // создать новый, второй поток исполнения
        t = new Thread(this, "Демонстрационный поток.");
        System.out.println("Дочерний поток создан: " + t);
        t.start(); // запустить поток исполнения
    }
    
    // Точка входа во второй поток исполнения
    public void run()
    {
        try
        {
            for (int i = 11; i > 0; i--)
            {
                System.out.println("Дочерний поток: " + i);
                Thread.sleep(2323);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Дочерний поток прерван.");
        }
        
        System.out.println("Дочерний поток завершён.");
    }
}

public class ThreadDemo
{
    public static void main(String[] args)
    {
        new NewThread(); // создать новый поток
        
        try
        {
            for (int i = 11; i > 0; i--)
            {
                System.out.println("Главный поток: " + i);
                Thread.sleep(2323);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Главный поток прерван.");
        }
        
        System.out.println("Главный поток завершён.");
    }
}