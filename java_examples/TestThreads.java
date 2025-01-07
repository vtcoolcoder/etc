import java.util.List;
import java.util.ArrayList;

public class TestThreads {
    private static final String[] NAMES = {
        "Первый", 
        "Второй", 
        "Третий", 
        "Четвёртый", 
        "Пятый", 
        "Шестой", 
        "Седьмой", 
        "Восьмой"
    };

    private static class MyThread extends Thread {
        private String threadName;
        
        public MyThread(String name) { threadName = name; }
        
        public void run() {
            for (int i = 0; i < 696; i++) {
                System.out.printf("%s:\t%d\n", threadName, i);
            }
        }
    }
    
    public static void main(String[] args) {
        startThreads(getNamedThreads(NAMES));
    }
    
    private static void startThreads(List<MyThread> threads) {
        for (MyThread currentThread : threads) {
            currentThread.start();
        }
    }
       
    private static List<MyThread> getNamedThreads(String... names) {
        List<MyThread> result = new ArrayList<>();
        
        for (String name : names) {
            result.add(new MyThread(name));
        }
        
        return result;
    }
}