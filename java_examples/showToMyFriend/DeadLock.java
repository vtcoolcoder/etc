// Пример взаимной блокировки
class A
{
    synchronized void foo(B b)
    {
        String name = Thread.currentThread().getName();
        
        System.out.println(name + " вошёл в метод A.foo()");
        
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {
            System.out.println("Класс A прерван.");
        }
        
        System.out.println(name + " пытается вызвать метод B.last()");
        b.last();
    }
    
    synchronized void last()
    {
        System.out.println("В методе A.last()");
    }
}

class B
{
    synchronized void bar(A a)
    {
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошёл в метод B.bar()");
        
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {
            System.out.println("Класс B прерван.");
        }
        
        System.out.println(name + " пытается вызвать метод A.last()");
        a.last();
    }
    
    synchronized void last()
    {
        System.out.println("В методе A.last()");
    }
}

public class DeadLock implements Runnable
{
    A a = new A();
    B b = new B();
    
    DeadLock()
    {
        Thread.currentThread().setName("Главный поток");
        Thread t = new Thread(this, "Соперничающий поток");
        t.start();
        
        // Получить блокировку для объекта a в данном потоке исполнения
        a.foo(b); 
        System.out.println("Назад в главный поток.");
    }
    
    public void run()
    {
        // Получить блокировку для объекта b в другом потоке исполнения
        b.bar(a);
        System.out.println("Назад в другой поток.");
    }
    
    public static void main(String[] args)
    {
        new DeadLock();
    }
}