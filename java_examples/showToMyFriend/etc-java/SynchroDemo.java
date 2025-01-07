public class SynchroDemo {
    public static void main(String[] args) throws InterruptedException {
        long before = System.currentTimeMillis();
        
        Task task = new Task();
        
        Thread firstThread = new Thread(task::firstTask);
        Thread secondThread = new Thread(task::secondTask);
        
        firstThread.start();
        secondThread.start();
        
        firstThread.join();
        secondThread.join();
        
        task.showCounter();
        
        long after = System.currentTimeMillis();
        
        System.out.println(after - before);
    }
}


class Task {
    private int counter;

    public synchronized void firstTask() { increment(); }   
    public synchronized void secondTask() { increment(); } 
    public void showCounter() { System.out.println(counter); }
    private void increment() { for (int i = 0; i < 1_000_000_000; i++, counter++) ; }
}