// Создать несколько потоков исполнения
class NewestThread implements Runnable
{
    String name; // имя потока исполнения
    Thread t;
    
    NewestThread(String threadName)
    {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        t.start(); // запустить поток на исполнение
    }
    
    // Точка входа в поток исполнения
    public void run()
    {
        try
        {
            for (int i = 5; i > 0; i--)
            {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println(name + " прерван.");
        }
        
        System.out.println(name + " завершён.");
    }
}

public class MultiThreadDemo
{
    public static void main(String[] args)
    {
        // Запустить потоки на исполнение
        new NewestThread("Один");
        new NewestThread("Два");
        new NewestThread("Три");
        new NewestThread("Четыре");
        new NewestThread("Пять");
        new NewestThread("Шесть");
        new NewestThread("Семь");
        new NewestThread("Восемь");
        new NewestThread("Девять");
        
        try
        {
            // ожидать завершения других потоков исполнения
            Thread.sleep(9669);
        }
        catch (InterruptedException ex)
        {
            System.out.println("Главный поток прерван.");
        }
        
        System.out.println("Главный поток завершён.");
    }
}