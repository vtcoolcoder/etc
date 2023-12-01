public class MultiThreadsDemo {
    private static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i <= 23; i++) { 
                System.out.println("classExtendsThread: " + i); 
            }
        }
    }
    
     
    private static class MyAnotherThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 23; i++) {
                System.out.println("classImplementsThread: " + i);
            }
        }
    }


    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 23; i++) {
                    System.out.println("anotherAnonymousClassThread: " + i);
                }
            }
        };        
    
        Thread anonymousClassThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 23; i++) { 
                    System.out.println("anonymousClassThread: " + i); 
                }
            }
        };  
        
        Thread lambdaThread = new Thread(() -> {
            for (int i = 1; i <= 23; i++) { 
                System.out.println("lambdaThread: " + i); 
            }
        });
        
        Thread anotherAnonymousClassThread = new Thread(runnable); 
        Thread refByMethodThread = new Thread(MultiThreadsDemo::showMessages); 
        Thread classExtendsThread = new MyThread();
        Thread classImplementsThread = new Thread(new MyAnotherThread());
            
        startThreads(lambdaThread, 
                     refByMethodThread, 
                     anonymousClassThread,
                     classImplementsThread,
                     classExtendsThread,
                     anotherAnonymousClassThread);
        
        for (int i = 0; i < 23; i++) { 
            System.out.println("mainThread: " + i); 
        }
    }  
    
    
    private static void showMessages() {
        for (int i = -23; i <= 0; i++) { 
            System.out.println("methodThread: " + i); 
        }
    } 
    
    
    private static void startThreads(Thread... threads) {
        for (Thread thread : threads) { 
            thread.start(); 
        }
    }
}