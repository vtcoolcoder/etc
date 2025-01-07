import java.util.List;
import java.util.ArrayList;


public class MultiThreadsRunTesting {
    private static int branchesCount = 2;
    private static int startedForkingDeep = 1;
    

    public static void main(String[] args) {
        try {
            startedForkingDeep = Integer.parseInt(args[0]);
            branchesCount = Integer.parseInt(args[1]);
        } catch (RuntimeException e) {}
        
        createTasks(startedForkingDeep);
    }
    
    
    private static void createTasks(int forkingDeep) {
        if (forkingDeep < 1) { return; }
        
        joiningThreads(startingThreads(fillThreadsList()));
        
        createTasks(--forkingDeep);
    }
    
    
    private static List<Thread> fillThreadsList() {
        List<Thread> threadsList = new ArrayList<>();
        
        for (int i = 0; i < branchesCount; i++) {
            Runnable task = MultiThreadsRunTesting::runIntoTheThread;
            Thread thread = new Thread(task);
            threadsList.add(thread);
        }
        
        return threadsList;
    }
    
    
    private static List<Thread> startingThreads(List<Thread> threadsList) {
        for (Thread thread : threadsList) {
            thread.start();
        }
        
        return threadsList;
    }
    
    
    private static void joiningThreads(List<Thread> threadsList) {
        try {
            for (Thread thread : threadsList) {
                thread.join();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static void runIntoTheThread() {
        String threadName = Thread.currentThread().getName();
        
        try {
            System.out.printf("Старт потока: %s...\n", threadName);
            Thread.sleep(2323);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.printf("Завершение потока: %s...\n", threadName);
        }
    }
}