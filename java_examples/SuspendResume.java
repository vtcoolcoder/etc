// Приостановка и возобновление исполнения потока современным способом
class NewThread69 implements Runnable
{
    String name; // имя потока исполнения
    Thread t;
    boolean suspendFlag;
    
    NewThread69(String threadName)
    {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        suspendFlag = false;
        t.start(); // запустить поток исполнения
    }
    
    // Точка входа в поток исполнения
    public void run()
    {
        try
        {
            for (int i = 15; i > 0; i--)
            {
                System.out.println(name + ": " + i);
                Thread.sleep(200);
                synchronized(this)
                {
                    while (suspendFlag)
                    {
                        wait();
                    }
                }
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println(name + " прерван.");
        }
        
        System.out.println(name + " завершён.");
    }
    
    synchronized void mySuspend()
    {
        suspendFlag = true;
    }
    
    synchronized void myResume()
    {
        suspendFlag = false;
        notify();
    }
}

public class SuspendResume
{
    public static void main(String[] args)
    {
        NewThread69 obj1 = new NewThread69("Один");
        NewThread69 obj2 = new NewThread69("Два");
        
        try
        {
            Thread.sleep(1000);
            
            obj1.mySuspend();
            System.out.println("Приостановка потока Один");
            Thread.sleep(1000);
            
            obj1.myResume();
            System.out.println("Возобновление потока Один");
            
            obj2.mySuspend();
            System.out.println("Приостановка потока Два");
            Thread.sleep(1000);
            
            obj2.myResume();
            System.out.println("Возобновление потока Два");
        }
        catch (InterruptedException ex)
        {
            System.out.println("Главный поток прерван.");
        }
        
        // Ожидать завершения потоков исполнения
        try
        {
            System.out.println("Ожидание завершения потоков.");
            obj1.t.join();
            obj2.t.join();
        }
        catch (InterruptedException ex)
        {
            System.out.println("Главный поток прерван.");
        }
        
        System.out.println("Главный поток завершён.");
    }
}