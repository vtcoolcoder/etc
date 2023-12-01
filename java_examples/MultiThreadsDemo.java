public class MultiThreadsDemo {
    @FunctionalInterface
    private interface MyFuncOnThread {
        void func(Thread thread) throws InterruptedException;
    }


    private static class MyThread extends Thread {
        @Override
        public void run() { printMessages("classExtends"); }
    }
    
     
    private static class MyAnotherThread implements Runnable {
        @Override
        public void run() { printMessages("classImplements"); }
    }


    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() { printMessages("anotherAnonymousClass"); }
        };        
    
        Thread anonymousClassThread = new Thread() {
            @Override
            public void run() { printMessages("anonymousClass"); }
        };  
        
        Thread lambdaThread = new Thread(() -> printMessages("lambda"));
        Thread anotherAnonymousClassThread = new Thread(runnable); 
        Thread refByMethodThread = new Thread(MultiThreadsDemo::showMessages); 
        Thread classExtendsThread = new MyThread();
        Thread classImplementsThread = new Thread(new MyAnotherThread());
        
        Thread[] threadsList = {
            lambdaThread, 
            refByMethodThread, 
            anonymousClassThread,
            classImplementsThread,
            classExtendsThread,
            anotherAnonymousClassThread
        };
            
        startThreads(threadsList, 
                     thread -> thread.start(),
                     thread -> thread.join());  
        
        printMessages("main");
    }  
    
    
    private static void showMessages() { printMessages("method"); }
    
    
    private static synchronized void printMessages(String name) {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "Thread: " + i);
        }
    }
    
    
    private static void startThreads(Thread[] threads, MyFuncOnThread... myFuncs) {       
        for (MyFuncOnThread myFunc : myFuncs) {
            try {
                for (Thread thread : threads) { 
                    myFunc.func(thread);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}