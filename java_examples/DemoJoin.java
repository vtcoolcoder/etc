// Применить метод join(), чтобы ожидать завершения потоков исполнения
class MyNewThread implements Runnable
{
    String name; // имя потока исполнения
    Thread t;
    
    MyNewThread(String threadName)
    {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        t.start(); // запустить поток исполнения
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

public class DemoJoin
{
    public static void main(String[] args)
    {
        MyNewThread obj1 = new MyNewThread("Один");
        MyNewThread obj2 = new MyNewThread("Два");
        MyNewThread obj3 = new MyNewThread("Три");
        
        System.out.println("Поток Один запущен: " + obj1.t.isAlive());
        System.out.println("Поток Два запущен: " + obj2.t.isAlive());
        System.out.println("Поток Три запущен: " + obj3.t.isAlive());
        
        // ожидать завершения потоков исполнения
        try
        {
            System.out.println("Ожидание завершения потоков.");
            obj1.t.join();
            obj2.t.join();
            obj3.t.join();
        }
        catch (InterruptedException ex)
        {
            System.out.println("Главный поток прерван.");
        }
        
        System.out.println("Поток Один запущен: " + obj1.t.isAlive());
        System.out.println("Поток Два запущен: " + obj2.t.isAlive());
        System.out.println("Поток Три запущен: " + obj3.t.isAlive());
        
        System.out.println("Главный поток завершён.");
    }
}