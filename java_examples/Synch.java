// Эта программа не синхронизирована
class CallMe
{
    void call(String msg)
    {
        System.out.print("[" + msg);
        
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            System.out.println("Прервано.");
        }
        
        System.out.println("]");
    }
}

class Caller implements Runnable
{
    String msg;
    CallMe target;
    Thread t;
    
    public Caller(CallMe target, String msg)
    {
        this.target = target;
        this.msg = msg;
        t = new Thread(this);
        t.start();
    }
    
    public void run()
    {
        target.call(msg);
    }
}

public class Synch
{
    public static void main(String[] args)
    {
        CallMe target = new CallMe();
        Caller obj1 = new Caller(target, "Добро пожаловать.");
        Caller obj2 = new Caller(target, "в синхронизированный");
        Caller obj3 = new Caller(target, "мир!");
        
        // ожидать завершения потока исполнения
        try
        {
            obj1.t.join();
            obj2.t.join();
            obj3.t.join();
        }
        catch (InterruptedException ex)
        {
            System.out.println("Прервано.");
        }
    }
}